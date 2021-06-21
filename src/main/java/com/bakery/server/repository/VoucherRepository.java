package com.bakery.server.repository;

import com.bakery.server.entity.VoucherEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends BaseRepository<VoucherEntity, Long> {
    VoucherEntity findByCode(String code);

    @Query("SELECT v FROM VoucherEntity v WHERE v.code LIKE '%:keyword%' OR v.name LIKE '%:keyword%'")
    Page<VoucherEntity> findByKeyword(String keyword, Pageable pageable);

    List<VoucherEntity> findByStatus(Integer status);
}
