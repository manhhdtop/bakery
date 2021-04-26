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

    List<CategoryEntity> findByParentIdIsNull();

    List<CategoryEntity> findByStatus(Integer status);

    CategoryEntity findBySlug(String slug);
}
