package com.bakery.server.repository;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.RoleEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Long> {
    RoleEntity findByCode(String code);

    List<RoleEntity> findByStatusIsNot(Status status);
}
