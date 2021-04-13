package com.bakery.server.repository;

import com.bakery.server.entity.ActionEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends BaseRepository<ActionEntity, Long> {
    ActionEntity findByCode(String code);

    Page<ActionEntity> findByStatusIsNot(Integer status, Pageable pageable);

    @Query("SELECT a FROM ActionEntity a WHERE a.code LIKE '%:keyword%' OR a.name LIKE '%:keyword%'")
    Page<ActionEntity> findByKeyword(String keyword, Pageable pageable);

    @Query("SELECT a FROM ActionEntity a WHERE a.status<>:status AND (a.code LIKE '%:keyword%' OR a.name LIKE '%:keyword%')")
    Page<ActionEntity> findByKeywordNotHidden(String keyword, Integer status, Pageable pageable);

    List<ActionEntity> findByStatus(Integer status);
}
