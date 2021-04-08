package com.bakery.server.repository;

import com.bakery.server.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByName(String name, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id=:categoryId")
    Page<ProductEntity> findByCategory(Long categoryId, Pageable pageable);
}
