package com.bakery.server.repository;

import com.bakery.server.entity.VoucherEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends BaseRepository<VoucherEntity, Long> {
    VoucherEntity findByCode(String code);

    @Query("SELECT v FROM VoucherEntity v WHERE v.code LIKE '%:keyword%' OR v.name LIKE '%:keyword%'")
    Page<VoucherEntity> findByKeyword(String keyword, Pageable pageable);

    List<VoucherEntity> findByStatus(Integer status);

    @Modifying
    @Query("UPDATE VoucherEntity v SET v.quantity=v.quantity-1, v.status = CASE WHEN v.quantity-1=0 THEN 2 ELSE 1 END WHERE v.id=:id AND v.status=1")
    void applyCoupon(Long id);
}
