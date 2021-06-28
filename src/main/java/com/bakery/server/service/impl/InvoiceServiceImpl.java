package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.*;
import com.bakery.server.exception.BadRequestException;
import com.bakery.server.model.request.CartItemRequest;
import com.bakery.server.model.request.InvoiceCreateDto;
import com.bakery.server.model.request.InvoiceUpdateStatusDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.InvoiceResponse;
import com.bakery.server.repository.*;
import com.bakery.server.service.InvoiceService;
import com.bakery.server.service.MailService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Constant;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Value("${base-url:}")
    private String baseUrl;
    @Value("${file.root-path:}")
    private String rootPath;

    @Override
    public ApiBaseResponse findAll(String keyword, Pageable pageable) {
        if (StringUtils.isBlank(keyword)) {
            return ApiBaseResponse.success(convertPage(invoiceRepository.findAll(pageable), pageable));
        }
        return ApiBaseResponse.success(convertPage(invoiceRepository.search(keyword.trim(), pageable), pageable));
    }

    @Override
    @Transactional
    public ApiBaseResponse createInvoice(InvoiceCreateDto request) {
        request.validate();

        VoucherEntity voucher = null;
        if (StringUtils.isNotBlank(request.getVoucherCode())) {
            voucher = voucherRepository.findByCode(request.getVoucherCode());
            AssertUtil.notNull(voucher, "voucher.notExist");
            AssertUtil.isTrue(voucher.getStatus() == 1, "voucher.notExist");
            Date now = new Date();
            if (voucher.getEndDate().before(now)) {
                voucher.setStatus(Status.EXPIRED.getStatus());
                throw new BadRequestException("voucher.notExist");
            }
            AssertUtil.isTrue(voucher.getStartDate().before(now), "voucher.notExist");
        }
        CatalogEntity province = catalogRepository.findById(request.getProvinceId()).orElse(null);
        AssertUtil.notNull(province, "catalog.province.not_found");
        CatalogEntity district = catalogRepository.findById(request.getDistrictId()).orElse(null);
        AssertUtil.notNull(district, "catalog.district.not_found");

        List<Long> productIds = request.getProducts().stream().map(CartItemRequest::getProductId).collect(Collectors.toList());
        List<ProductEntity> productEntities = productRepository.findAllById(productIds);
        AssertUtil.isTrue(productIds.size() == productEntities.size(), "checkout.products.not_found");

        String invoiceId = Utils.generateInvoiceId();
        InvoiceEntity invoice = modelMapper.map(request, InvoiceEntity.class);
        invoice.setInvoiceId(invoiceId);
        invoice.setProvince(province);
        invoice.setDistrict(district);
        invoice = invoiceRepository.save(invoice);

        Long id = invoice.getId();
        List<InvoiceProductEntity> items = productEntities.stream().map(e -> {
            CartItemRequest item = request.getProducts().stream().filter(e1 -> e1.getProductId().equals(e.getId())).findFirst().orElse(null);
            if (item == null) {
                item = new CartItemRequest(e.getId(), 0);
            }
            InvoiceProductEntity entity = new InvoiceProductEntity();
            entity.setInvoiceId(id);
            entity.setProduct(e);
            entity.setQuantity(item.getQuantity());
            return entity;
        }).collect(Collectors.toList());
        items = invoiceProductRepository.saveAll(items);
        Long totalAmount = items.stream().map(e -> e.getProduct().getPrice() * e.getQuantity()).mapToLong(value -> value).sum();
        if (voucher != null) {
            if (voucher.getMinAmount() != null) {
                AssertUtil.isTrue(voucher.getMinAmount() <= totalAmount, "voucher.not_enough_condition");
            }
            if (voucher.getMaxAmount() != null) {
                AssertUtil.isTrue(voucher.getMaxAmount() >= totalAmount, "voucher.not_enough_condition");
            }
            if (voucher.getType().equals(Constant.VoucherType.AMOUNT)) {
                totalAmount -= voucher.getValue();
            } else {
                long value = (long) (voucher.getValue() * 0.1);
                if (voucher.getMinRefund() != null) {
                    value = Math.max(value, voucher.getMinAmount());
                }
                if (voucher.getMaxRefund() != null) {
                    value = Math.min(value, voucher.getMaxRefund());
                }
                totalAmount -= totalAmount * value;
            }
            voucherRepository.applyCoupon(voucher.getId());
            invoice.setVoucher(voucher);
        }
        invoice.setProducts(items);
        invoice.setTotalAmount(totalAmount);
        invoice = invoiceRepository.save(invoice);

        sendMailConfirm(invoice);

        return ApiBaseResponse.success(convert(invoice));
    }

    @Override
    public ApiBaseResponse updateStatus(InvoiceUpdateStatusDto request) {
        InvoiceEntity invoice = invoiceRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(invoice, "invoice.not_found");
        request.validate(invoice.getStatus());
        invoice.setStatus(request.getStatus());
        if (request.getDescription() != null) {
            invoice.setStatusDescription(request.getDescription().trim());
        }
        invoice = invoiceRepository.save(invoice);
        return ApiBaseResponse.success(convert(invoice));
    }

    private InvoiceResponse convert(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, InvoiceResponse.class);
    }

    private List<InvoiceResponse> convertList(List<InvoiceEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<InvoiceResponse>>() {
        }.getType();
        return modelMapper.map(entities, type);
    }

    private Page<InvoiceResponse> convertPage(Page<InvoiceEntity> page, Pageable pageable) {
        List<InvoiceEntity> entities = page.getContent();
        List<InvoiceResponse> responses = convertList(entities);
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }

    private void sendMailConfirm(InvoiceEntity invoiceEntity) {
        String templateCode = "INVOICE_TEMPLATE";
        MailTemplateEntity mailTemplateEntity = mailTemplateRepository.findByCode(templateCode);
        if (mailTemplateEntity != null && mailTemplateEntity.getStatus() == 1) {
            String subject = MessageFormat.format(mailTemplateEntity.getSubject(), invoiceEntity.getInvoiceId());
            StringBuilder items = new StringBuilder();
            List<String> cids = new ArrayList<>();
            List<File> files = new ArrayList<>();
            items.append("<table style=\"width: 100%\">");
            for (InvoiceProductEntity p : invoiceEntity.getProducts()) {
                String uri = p.getProduct().getImages().get(0).getUri();
                String cid = "image-" + p.getProduct().getId();
                cids.add(cid);
                files.add(new File(rootPath + uri));
                items.append("  <tr>");
                items.append("      <td style=\"width: 20%\">");
//                items.append("      <img style=\"width: 100%; height: auto\" src=\"data:image/jpg;base64,").append(base64).append("\" alt=\"product image\">");
                items.append("      <img style=\"width: 100%; height: auto\" src=\"cid:").append(cid).append("\" alt=\"product image\">");
                items.append("      </td>");
                items.append("      <td style=\"width: 80%; vertical-align: top\">");
                items.append("        <h5>").append(p.getProduct().getName()).append("</h5>");
                items.append("        <span>Số lượng: ").append(p.getQuantity()).append("</span><br/>");
                items.append("        <span>Giá: ").append(String.format("%,d", p.getProduct().getPrice())).append("₫</span>");
                items.append("      </td>");
                items.append("  </tr>");
            }
            items.append("</table>");

            String message = MessageFormat.format(mailTemplateEntity.getMessage(),
                    invoiceEntity.getCustomerName(),
                    invoiceEntity.getInvoiceId(),
                    invoiceEntity.getCustomerName(),
                    invoiceEntity.getCustomerPhone(),
                    invoiceEntity.getCustomerEmail(),
                    invoiceEntity.getAddress(),
                    items.toString()
            );

            mailService.sendMail(invoiceEntity.getCustomerEmail(), subject, message, files, cids);
        }
    }
}
