package com.bakery.server.service.impl;

import com.bakery.server.entity.OptionTypeEntity;
import com.bakery.server.model.request.OptionTypeCreateDto;
import com.bakery.server.model.request.OptionTypeUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.OptionTypeResponse;
import com.bakery.server.repository.OptionTypeRepository;
import com.bakery.server.service.OptionTypeService;
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
public class OptionTypeServiceImpl implements OptionTypeService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OptionTypeRepository optionTypeRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(optionTypeRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByKeyword(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(optionTypeRepository.findByNameContaining(keyword, pageable)));
    }

    @Override
    public ApiBaseResponse save(OptionTypeCreateDto request) {
        OptionTypeEntity productOptionEntity = modelMapper.map(request, OptionTypeEntity.class);
        productOptionEntity = optionTypeRepository.save(productOptionEntity);
        return ApiBaseResponse.success(convert(productOptionEntity));
    }

    @Override
    public ApiBaseResponse update(OptionTypeUpdateDto request) {
        OptionTypeEntity actionOld = optionTypeRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(actionOld, "action.not_exist");

        modelMapper.map(request, actionOld);

        actionOld = optionTypeRepository.save(actionOld);
        return ApiBaseResponse.success(convert(actionOld));
    }

    @Override
    public void delete(Long id) {
        OptionTypeEntity entityOld = optionTypeRepository.findById(id).orElse(null);
        if (entityOld != null) {
            entityOld.setDeleted(1);
            optionTypeRepository.save(entityOld);
        }
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(convertList(optionTypeRepository.findByStatus(status)));
    }

    private OptionTypeResponse convert(OptionTypeEntity optionTypeEntity) {
        if (optionTypeEntity == null) {
            return null;
        }
        return modelMapper.map(optionTypeEntity, OptionTypeResponse.class);
    }

    private List<OptionTypeResponse> convertList(List<OptionTypeEntity> optionEntities) {
        if (CollectionUtils.isEmpty(optionEntities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<OptionTypeResponse>>() {
        }.getType();
        return modelMapper.map(optionEntities, type);
    }

    private Page<OptionTypeResponse> convertPage(Page<OptionTypeEntity> page) {
        List<OptionTypeEntity> optionEntities = page.getContent();
        return new PageImpl<>(convertList(optionEntities), page.getPageable(), page.getTotalElements());
    }
}
