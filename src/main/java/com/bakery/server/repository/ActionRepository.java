package com.bakery.server.repository;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.ActionEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends BaseRepository<ActionEntity, Long> {
    ActionEntity findByCode(String code);

    List<ActionEntity> findByStatusIsNot(Status status);
}
