package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.InvoiceEntity;
import com.bakery.server.entity.InvoiceProductEntity;
import com.bakery.server.entity.ProductEntity;
import com.bakery.server.entity.VoucherEntity;
import com.bakery.server.exception.BadRequestException;
import com.bakery.server.model.request.CartItemRequest;
import com.bakery.server.model.request.InvoiceCreateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.InvoiceResponse;
import com.bakery.server.repository.InvoiceProductRepository;
import com.bakery.server.repository.InvoiceRepository;
import com.bakery.server.repository.ProductRepository;
import com.bakery.server.repository.VoucherRepository;
import com.bakery.server.service.InvoiceService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Constant;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public ApiBaseResponse findAll(String phoneNumber, Pageable pageable) {
        if (StringUtils.isBlank(phoneNumber)) {
            return ApiBaseResponse.success(convertPage(invoiceRepository.findAll(pageable), pageable));
        }
        return ApiBaseResponse.success(convertPage(invoiceRepository.findByCustomerPhone(phoneNumber.trim(), pageable), pageable));
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

        List<Long> productIds = request.getProducts().stream().map(CartItemRequest::getProductId).collect(Collectors.toList());
        List<ProductEntity> productEntities = productRepository.findAllById(productIds);
        AssertUtil.isTrue(productIds.size() == productEntities.size(), "checkout.products.not_found");

        String invoiceId = Utils.generateInvoiceId();
        InvoiceEntity invoice = modelMapper.map(request, InvoiceEntity.class);
        invoice.setInvoiceId(invoiceId);
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
        }
        invoice.setProducts(items);
        invoice.setTotalAmount(totalAmount);
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
        Type type = new TypeToken<List<InvoiceResponse>>() {
        }.getType();
        List<InvoiceResponse> responses = modelMapper.map(entities, type);
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }
}
