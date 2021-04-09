package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.service.RoleService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(roleRepository.findAll(pageable));
    }

    @Override
    public ApiBaseResponse findByName(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(roleRepository.findByName(keyword, pageable));
    }

    @Override
    public ApiBaseResponse findByNameNotHidden(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(roleRepository.findByNameAndStatusIsNot(keyword, Status.ADMINISTRATOR.getStatus(), pageable));
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(roleRepository.findByStatus(status));
    }

    @Override
    public ApiBaseResponse save(RoleCreateDto roleCreateDto) {
        RoleEntity roleOld = roleRepository.findByCode(roleCreateDto.getCode());
        AssertUtil.isNull(roleOld, "role.create.code.exist");
        RoleEntity roleEntity = modelMapper.map(roleCreateDto, RoleEntity.class);
        return ApiBaseResponse.success(roleRepository.save(roleEntity));
    }

    @Override
    public ApiBaseResponse update(RoleUpdateDto roleUpdateDto) {
        RoleEntity roleOld = roleRepository.findById(roleUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(roleOld, "role.not_exist");
        modelMapper.map(roleUpdateDto, roleOld);
        return ApiBaseResponse.success(roleRepository.save(roleOld));
    }

    @Override
    public void delete(Long id) {
        RoleEntity roleOld = roleRepository.findById(id).orElse(null);
        if (roleOld != null) {
            roleOld.setDeleted(1);
            roleRepository.save(roleOld);
        }
    }

    @Override
    public ApiBaseResponse findAllStatusNotHidden(Pageable pageable) {
        return ApiBaseResponse.success(roleRepository.findByStatusIsNot(Status.ADMINISTRATOR.getStatus(), pageable));
    }
}
