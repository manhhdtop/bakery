package com.bakery.server.repository;

import com.bakery.server.entity.ProductOptionEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends BaseRepository<ProductOptionEntity, Long> {
    List<ProductOptionEntity> findByProductId(Long productId);
}
