package com.bakery.server.repository;

import com.bakery.server.entity.ProductEntity;
import com.bakery.server.entity.ProductRateEntity;
import com.bakery.server.repository.customer.ProductRepositoryCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRateRepository extends JpaRepository<ProductRateEntity, Long> {
    Page<ProductRateEntity> findByProductIdOrderByCreatedDateDesc(Long productId, Pageable pageable);
}
