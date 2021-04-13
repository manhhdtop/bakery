package com.bakery.server.service;

import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface ActionService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByKeyword(String keyword, Pageable pageable);

    ApiBaseResponse findByKeywordNotHidden(String keyword, Pageable pageable);

    ApiBaseResponse findByStatusNotHidden(Pageable pageable);

    ApiBaseResponse findByStatus(Integer status);

    ApiBaseResponse save(ActionCreateDto roleCreateDto);

    ApiBaseResponse update(ActionUpdateDto roleUpdateDto);

    void delete(Long id);
}
