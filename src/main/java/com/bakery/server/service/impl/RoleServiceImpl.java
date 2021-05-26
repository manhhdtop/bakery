package com.bakery.server.service.impl;

import com.bakery.server.entity.ActionEntity;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.model.response.ActionResponse;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.RoleResponse;
import com.bakery.server.repository.ActionRepository;
import com.bakery.server.repository.RoleRepository;
import com.bakery.server.service.RoleService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(roleRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByName(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(roleRepository.findByName(keyword, pageable)));
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(convertList(roleRepository.findByStatus(status)));
    }

    @Override
    public ApiBaseResponse save(RoleCreateDto roleCreateDto) {
        roleCreateDto.validData();
        RoleEntity roleOld = roleRepository.findByCode(roleCreateDto.getCode());
        AssertUtil.isNull(roleOld, "role.create.code.exist");
        RoleEntity roleEntity = modelMapper.map(roleCreateDto, RoleEntity.class);
        List<ActionEntity> actionEntities = actionRepository.findAllById(roleCreateDto.getActionIds());
        AssertUtil.notEmpty(actionEntities, "role.create.actions.not_found");
        AssertUtil.isTrue(actionEntities.size() == roleCreateDto.getActionIds().size(), "role.create.actions.not_found");
        roleEntity.setActions(actionEntities);

        return ApiBaseResponse.success(convert(roleRepository.save(roleEntity)));
    }

    @Override
    public ApiBaseResponse update(RoleUpdateDto roleUpdateDto) {
        roleUpdateDto.validData();
        RoleEntity roleOld = roleRepository.findById(roleUpdateDto.getId()).orElse(null);
        RoleEntity roleByCode = roleRepository.findByCode(roleUpdateDto.getCode());
        AssertUtil.notNull(roleOld, "role.not_exist");
        modelMapper.map(roleUpdateDto, roleOld);
        List<ActionEntity> actionEntities = actionRepository.findAllById(roleUpdateDto.getActionIds());
        AssertUtil.notEmpty(actionEntities, "role.create.actions.not_found");
        AssertUtil.isTrue(actionEntities.size() == roleUpdateDto.getActionIds().size(), "role.create.actions.not_found");
        roleOld.setActions(actionEntities);
        return ApiBaseResponse.success(convert(roleRepository.save(roleOld)));
    }

    @Override
    public void delete(Long id) {
        RoleEntity roleOld = roleRepository.findById(id).orElse(null);
        if (roleOld != null) {
            roleOld.setDeleted(1);
            roleRepository.save(roleOld);
        }
    }

    private RoleResponse convert(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return modelMapper.map(roleEntity, RoleResponse.class);
    }

    private List<RoleResponse> convertList(List<RoleEntity> roleEntities) {
        if (CollectionUtils.isEmpty(roleEntities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<RoleResponse>>() {
        }.getType();
        return modelMapper.map(roleEntities, type);
    }

    private Page<RoleResponse> convertPage(Page<RoleEntity> page) {
        List<RoleEntity> roleEntities = page.getContent();
        return new PageImpl<>(convertList(roleEntities), page.getPageable(), page.getTotalElements());
    }
}
