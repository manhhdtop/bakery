package com.bakery.server.repository;

import com.bakery.server.entity.InvoiceEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends BaseRepository<InvoiceEntity, Long> {
    InvoiceEntity findByInvoiceId(String invoiceId);

    Page<InvoiceEntity> findByCustomerPhone(String customerPhone, Pageable pageable);

    @Query("SELECT i FROM InvoiceEntity i WHERE i.invoiceId=:keyword OR i.customerPhone=:keyword")
    Page<InvoiceEntity> search(String keyword, Pageable pageable);

    List<InvoiceEntity> findByCreatedDateAfter(Long startDate);
}
