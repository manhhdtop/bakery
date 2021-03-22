package com.bakery.server.service;

import com.bakery.server.entity.ActionEntity;
import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;

import java.util.List;

public interface ActionService {
    List<ActionEntity> findAll();

    ActionEntity save(ActionCreateDto roleCreateDto);

    ActionEntity update(ActionUpdateDto roleUpdateDto);

    void delete(Long id);

    List<ActionEntity> findAllStatusNotHidden();
}
