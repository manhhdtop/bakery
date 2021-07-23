package com.bakery.server.repository;

import com.bakery.server.entity.ProductEntity;
import com.bakery.server.repository.customer.ProductRepositoryCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends ProductRepositoryCustomer, JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id=:categoryId")
    Page<ProductEntity> findByCategory(Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM product p WHERE p.slug  REGEXP concat('^', :slug, '(?:-#[0-9]+)?$') ORDER BY p.slug DESC LIMIT 1", nativeQuery = true)
    ProductEntity findBySlugLike(String slug);

    @Query(value = "SELECT * FROM product p ORDER BY p.price LIMIT 1", nativeQuery = true)
    Optional<ProductEntity> findProductMinPrice();

    @Query(value = "SELECT * FROM product p ORDER BY p.price DESC LIMIT 1", nativeQuery = true)
    Optional<ProductEntity> findProductMaxPrice();

    Optional<ProductEntity> findFirstByNameContainingOrderByPriceAsc(String name);

    Optional<ProductEntity> findFirstByNameContainingOrderByPriceDesc(String name);

    @Query(value = "SELECT * FROM product p WHERE p.category=:categoryId ORDER BY p.price LIMIT 1", nativeQuery = true)
    Optional<ProductEntity> findByCategoryOrderByPriceAsc(Long categoryId);

    @Query(value = "SELECT * FROM product p WHERE p.category=:categoryId ORDER BY p.price DESC LIMIT 1", nativeQuery = true)
    Optional<ProductEntity> findByCategoryOrderByPriceDesc(Long categoryId);
}
