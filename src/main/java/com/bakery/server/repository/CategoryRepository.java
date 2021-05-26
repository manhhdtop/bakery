package com.bakery.server.repository;

import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.repository.customer.CategoryRepositoryCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CategoryRepositoryCustomer, JpaRepository<CategoryEntity, Long> {
    Page<CategoryEntity> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT c FROM CategoryEntity c WHERE c.status=1 AND c.parent IS NULL")
    List<CategoryEntity> findListParent();

    List<CategoryEntity> findByStatus(Integer status);

    CategoryEntity findBySlug(String slug);

    @Query(value = "SELECT * FROM category c WHERE c.slug  REGEXP concat('^', :slug, '(?:-#[0-9]+)?$') ORDER BY c.slug DESC LIMIT 1", nativeQuery = true)
    CategoryEntity findBySlugLike(String slug);
}
