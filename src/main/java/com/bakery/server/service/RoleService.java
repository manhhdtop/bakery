package com.bakery.server.service;

import com.bakery.server.entity.RoleEntity;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;

import java.util.List;

public interface RoleService {
    List<RoleEntity> findAll();

    List<RoleEntity> findAllStatusNotHidden();

    RoleEntity save(RoleCreateDto roleCreateDto);

    RoleEntity update(RoleUpdateDto roleUpdateDto);

    void delete(Long id);
}
