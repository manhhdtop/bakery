package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.ActionEntity;
import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.repository.ActionRepository;
import com.bakery.server.service.ActionService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ActionRepository actionRepository;

    @Override
    public List<ActionEntity> findAll() {
        return actionRepository.findAll();
    }

    @Override
    public ActionEntity save(ActionCreateDto actionCreateDto) {
        ActionEntity actionOld = actionRepository.findByCode(actionCreateDto.getCode());
        AssertUtil.isNull(actionOld, "action.create.code.exist");

        ActionEntity roleEntity = modelMapper.map(actionCreateDto, ActionEntity.class);

        return actionRepository.save(roleEntity);
    }

    @Override
    public ActionEntity update(ActionUpdateDto actionUpdateDto) {
        ActionEntity actionOld = actionRepository.findById(actionUpdateDto.getId()).orElse(null);
        AssertUtil.notNull(actionOld, "action.notExist");

        modelMapper.map(actionUpdateDto, actionOld);

        return actionRepository.save(actionOld);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ActionEntity> findAllStatusNotHidden() {
        return actionRepository.findByStatusIsNot(Status.ADMINISTRATOR);
    }
}
