package com.bakery.server.service.impl;

import com.bakery.server.entity.VoucherEntity;
import com.bakery.server.model.request.VoucherCreateDto;
import com.bakery.server.model.request.VoucherUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.VoucherResponse;
import com.bakery.server.repository.VoucherRepository;
import com.bakery.server.service.VoucherService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.VoucherUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VoucherRepository voucherRepository;

    @Value("${voucher.default-length:8}")
    private Integer defaultLength;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(voucherRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByKeyword(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(voucherRepository.findByKeyword(keyword, pageable)));
    }

    @Override
    public ApiBaseResponse findByCode(String code) {
        return ApiBaseResponse.success(convert(voucherRepository.findByCode(code.trim())));
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(convertList(voucherRepository.findByStatus(status)));
    }

    @Override
    public ApiBaseResponse save(VoucherCreateDto request) {
        request.validate();
        VoucherEntity voucherByCode = voucherRepository.findByCode(request.getCode());
        AssertUtil.isNull(voucherByCode, "voucher.code.exist");
        VoucherEntity voucherEntity = modelMapper.map(request, VoucherEntity.class);
        voucherEntity = voucherRepository.save(voucherEntity);
        return ApiBaseResponse.success(convert(voucherEntity));
    }

    @Override
    public ApiBaseResponse update(VoucherUpdateDto request) {
        request.validate();
        VoucherEntity entity = voucherRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "voucher.notExist");
        AssertUtil.isTrue(entity.getStatus().equals(1), "voucher.update.status.notValid");
        modelMapper.map(request, entity);
        entity = voucherRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        VoucherEntity voucher = voucherRepository.findById(id).orElse(null);
        if (voucher != null && !voucher.getStatus().equals(status)) {
            voucher.setStatus(status);
            voucherRepository.save(voucher);
        }
    }

    @Override
    public void delete(Long id) {
        VoucherEntity voucher = voucherRepository.findById(id).orElse(null);
        if (voucher != null) {
            voucher.setDeleted(1);
            voucherRepository.save(voucher);
        }
    }

    @Override
    public ApiBaseResponse generateCode() {
        String code;
        boolean exist = true;
        VoucherEntity voucher;
        do {
            code = VoucherUtil.generate(defaultLength);
            voucher = voucherRepository.findByCode(code);
            if (voucher == null) {
                exist = false;
            }
        } while (exist);
        return ApiBaseResponse.success(code);
    }

    private VoucherResponse convert(VoucherEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, VoucherResponse.class);
    }

    private List<VoucherResponse> convertList(List<VoucherEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<VoucherResponse>>() {
        }.getType();
        return modelMapper.map(entities, type);
    }

    private Page<VoucherResponse> convertPage(Page<VoucherEntity> page) {
        List<VoucherEntity> entities = page.getContent();
        return new PageImpl<>(convertList(entities), page.getPageable(), page.getTotalElements());
    }
}
