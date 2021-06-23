package com.bakery.server.service;

import com.bakery.server.model.request.VoucherCreateDto;
import com.bakery.server.model.request.VoucherUpdateDto;
import com.bakery.server.model.request.VoucherUpdateStatusDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface VoucherService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByKeyword(String keyword, Pageable pageable);

    ApiBaseResponse findByCode(String code);

    ApiBaseResponse findByStatus(Integer status);

    ApiBaseResponse save(VoucherCreateDto request);

    ApiBaseResponse update(VoucherUpdateDto request);

    void updateStatus(VoucherUpdateStatusDto request);

    void delete(Long id);

    ApiBaseResponse generateCode();

    ApiBaseResponse checkCode(String code);
}
