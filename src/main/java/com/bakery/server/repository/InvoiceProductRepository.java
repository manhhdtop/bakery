package com.bakery.server.repository;

import com.bakery.server.entity.InvoiceProductEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductRepository extends BaseRepository<InvoiceProductEntity, Long> {
}
