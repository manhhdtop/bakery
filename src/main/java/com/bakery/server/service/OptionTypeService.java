package com.bakery.server.service;

import com.bakery.server.model.request.OptionCreateDto;
import com.bakery.server.model.request.OptionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface OptionTypeService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByKeyword(String keyword, Pageable pageable);

    ApiBaseResponse findByStatus(Integer status);

    ApiBaseResponse save(OptionCreateDto roleCreateDto);

    ApiBaseResponse update(OptionUpdateDto roleUpdateDto);

    void delete(Long id);
}
