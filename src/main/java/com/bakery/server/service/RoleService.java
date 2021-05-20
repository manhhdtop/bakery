package com.bakery.server.service;

import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByName(String keyword, Pageable pageable);

    ApiBaseResponse findByStatus(Integer status);

    ApiBaseResponse save(RoleCreateDto roleCreateDto);

    ApiBaseResponse update(RoleUpdateDto roleUpdateDto);

    void delete(Long id);
}
