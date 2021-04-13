package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.ActionEntity;
import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.ActionRepository;
import com.bakery.server.service.ActionService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ActionRepository actionRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(actionRepository.findAll(pageable));
    }

    @Override
    public ApiBaseResponse findByKeyword(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(actionRepository.findByKeyword(keyword, pageable));
    }

    @Override
    public ApiBaseResponse findByKeywordNotHidden(String keyword, Pageable pageable) {
        return ApiBaseResponse.success(actionRepository.findByKeywordNotHidden(keyword, Status.ADMINISTRATOR.getStatus(), pageable));
    }

    @Override
    public ApiBaseResponse save(ActionCreateDto actionCreateDto) {
        actionCreateDto.setCode(actionCreateDto.getCode().trim().toUpperCase());
        ActionEntity actionOld = actionRepository.findByCode(actionCreateDto.getCode());
        AssertUtil.isNull(actionOld, "action.create.code.exist");

        ActionEntity actionEntity = modelMapper.map(actionCreateDto, ActionEntity.class);

        return ApiBaseResponse.success(actionRepository.save(actionEntity));
    }

    @Override
    public ApiBaseResponse update(ActionUpdateDto actionUpdateDto) {
        actionUpdateDto.setCode(actionUpdateDto.getCode().trim().toUpperCase());
        ActionEntity actionOld = actionRepository.findById(actionUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(actionOld, "action.not_exist");
        ActionEntity actionByCode = actionRepository.findByCode(actionUpdateDto.getCode());
        AssertUtil.isTrue(actionOld.getId().equals(actionByCode.getId()), "action.create.code.exist");

        modelMapper.map(actionUpdateDto, actionOld);

        return ApiBaseResponse.success(actionRepository.save(actionOld));
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
    public ApiBaseResponse findByStatusNotHidden(Pageable pageable) {
        return ApiBaseResponse.success(actionRepository.findByStatusIsNot(Status.ADMINISTRATOR.getStatus(), pageable));
    }

    @Override
    public ApiBaseResponse findByStatus(Integer status) {
        return ApiBaseResponse.success(actionRepository.findByStatus(status));
    }
}
