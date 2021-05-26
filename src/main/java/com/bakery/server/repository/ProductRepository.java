package com.bakery.server.repository;

import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.entity.ProductEntity;
import com.bakery.server.repository.customer.ProductRepositoryCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ProductRepositoryCustomer, JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByName(String name, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id=:categoryId")
    Page<ProductEntity> findByCategory(Long categoryId, Pageable pageable);

    ProductEntity findBySlug(String slug);

    @Query(value = "SELECT * FROM category c WHERE c.slug  REGEXP concat('^', :slug, '(?:-#[0-9]+)?$') ORDER BY c.slug DESC", nativeQuery = true)
    ProductEntity findBySlugLike(String slug);
}
