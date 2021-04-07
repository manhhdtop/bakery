package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.service.RoleService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity save(RoleCreateDto roleCreateDto) {
        RoleEntity roleOld = roleRepository.findByCode(roleCreateDto.getCode());
        AssertUtil.isNull(roleOld, "role.create.code.exist");

        RoleEntity roleEntity = modelMapper.map(roleCreateDto, RoleEntity.class);

        return roleRepository.save(roleEntity);
    }

    @Override
    public RoleEntity update(RoleUpdateDto roleUpdateDto) {
        RoleEntity roleOld = roleRepository.findById(roleUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(roleOld, "role.notExist");

        modelMapper.map(roleUpdateDto, roleOld);

        return roleRepository.save(roleOld);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<RoleEntity> findAllStatusNotHidden() {
        return roleRepository.findByStatusIsNot(Status.ADMINISTRATOR);
    }
}
