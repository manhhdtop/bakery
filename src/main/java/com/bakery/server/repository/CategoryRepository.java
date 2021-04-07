package com.bakery.server.repository;

import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.repository.customer.CategoryRepositoryCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CategoryRepositoryCustomer, JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByNameContaining(String name);

    List<CategoryEntity> findByParentIdIsNull();
}
