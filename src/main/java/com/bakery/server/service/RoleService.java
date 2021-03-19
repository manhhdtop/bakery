package com.bakery.server.service;

import com.bakery.server.entity.RoleEntity;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;

public interface RoleService {
    RoleEntity save(RoleCreateDto roleCreateDto);

    RoleEntity update(RoleUpdateDto roleUpdateDto);

    void delete(Long id);
}
