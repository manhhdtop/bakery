package com.bakery.server.service;

import com.bakery.server.model.request.OptionTypeCreateDto;
import com.bakery.server.model.request.OptionTypeUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface OptionTypeService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByKeyword(String keyword, Pageable pageable);

    ApiBaseResponse findByStatus(Integer status);

    ApiBaseResponse save(OptionTypeCreateDto roleCreateDto);

    ApiBaseResponse update(OptionTypeUpdateDto roleUpdateDto);

    void delete(Long id);
}
