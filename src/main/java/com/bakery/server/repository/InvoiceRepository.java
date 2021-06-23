package com.bakery.server.repository;

import com.bakery.server.entity.InvoiceEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends BaseRepository<InvoiceEntity, Long> {
    InvoiceEntity findByInvoiceId(String invoiceId);

    Page<InvoiceEntity> findByCustomerPhone(String customerPhone, Pageable pageable);
}
