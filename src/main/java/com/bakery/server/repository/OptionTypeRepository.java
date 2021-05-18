package com.bakery.server.repository;

import com.bakery.server.entity.OptionTypeEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionTypeRepository extends BaseRepository<OptionTypeEntity, Long> {
    Page<OptionTypeEntity> findByNameContaining(String keyword, Pageable pageable);

    List<OptionTypeEntity> findByStatus(Integer status);
}
