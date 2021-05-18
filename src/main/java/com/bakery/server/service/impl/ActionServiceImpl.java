package com.bakery.server.service.impl;

import com.bakery.server.entity.ActionEntity;
import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.model.response.ActionResponse;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.ActionRepository;
import com.bakery.server.service.ActionService;
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
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ActionRepository actionRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(actionRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByKeyword(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(actionRepository.findByKeyword(keyword, pageable)));
    }

    @Override
    public ApiBaseResponse save(ActionCreateDto actionCreateDto) {
        actionCreateDto.setCode(actionCreateDto.getCode().trim().toUpperCase());
        ActionEntity actionOld = actionRepository.findByCode(actionCreateDto.getCode());
        AssertUtil.isNull(actionOld, "action.create.code.exist");

        ActionEntity actionEntity = modelMapper.map(actionCreateDto, ActionEntity.class);
        actionEntity = actionRepository.save(actionEntity);
        return ApiBaseResponse.success(convert(actionEntity));
    }

    @Override
    public ApiBaseResponse update(ActionUpdateDto actionUpdateDto) {
        actionUpdateDto.setCode(actionUpdateDto.getCode().trim().toUpperCase());
        ActionEntity actionOld = actionRepository.findById(actionUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(actionOld, "action.not_exist");
        ActionEntity actionByCode = actionRepository.findByCode(actionUpdateDto.getCode());
        if (actionByCode != null) {
            AssertUtil.isTrue(actionOld.getId().equals(actionByCode.getId()), "action.create.code.exist");
        }

        modelMapper.map(actionUpdateDto, actionOld);

        actionOld = actionRepository.save(actionOld);
        return ApiBaseResponse.success(convert(actionOld));
    }

    @Override
    public void delete(Long id) {
        ActionEntity actionOld = actionRepository.findById(id).orElse(null);
        if (actionOld != null) {
            actionOld.setDeleted(1);
            actionRepository.save(actionOld);
        }
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(convertList(actionRepository.findByStatus(status)));
    }

    private ActionResponse convert(ActionEntity actionEntity) {
        if (actionEntity == null) {
            return null;
        }
        return modelMapper.map(actionEntity, ActionResponse.class);
    }

    private List<ActionResponse> convertList(List<ActionEntity> actionEntities) {
        if (CollectionUtils.isEmpty(actionEntities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ActionResponse>>() {
        }.getType();
        return modelMapper.map(actionEntities, type);
    }

    private Page<ActionResponse> convertPage(Page<ActionEntity> page) {
        List<ActionEntity> actionEntities = page.getContent();
        return new PageImpl<>(convertList(actionEntities), page.getPageable(), page.getTotalElements());
    }
}
