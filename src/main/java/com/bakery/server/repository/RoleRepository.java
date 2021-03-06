package com.bakery.server.repository;

import com.bakery.server.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByCode(String code);

    Page<RoleEntity> findByCodeContaining(String code, Pageable pageable);

    List<RoleEntity> findByStatus(Integer status);

    Page<RoleEntity> findByName(String keyword, Pageable pageable);
}
