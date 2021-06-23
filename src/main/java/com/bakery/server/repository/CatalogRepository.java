package com.bakery.server.repository;

import com.bakery.server.entity.CatalogEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends BaseRepository<CatalogEntity, Long> {
    @Query("SELECT c FROM CatalogEntity c WHERE c.status=1 AND c.parentCode='0' ORDER BY c.code")
    List<CatalogEntity> getListProvince();

    @Query("SELECT c FROM CatalogEntity c WHERE c.status=1 AND c.parentCode=(SELECT code FROM CatalogEntity WHERE id=:provinceId) ORDER BY c.code")
    List<CatalogEntity> getListDistrict(Long provinceId);
}
