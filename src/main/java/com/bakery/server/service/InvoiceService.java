package com.bakery.server.service;

import com.bakery.server.model.request.InvoiceCreateDto;
import com.bakery.server.model.request.InvoiceUpdateStatusDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;


public interface InvoiceService {
    ApiBaseResponse findAll(String keyword, Pageable pageable);

    ApiBaseResponse createInvoice(InvoiceCreateDto invoiceCreateDto);

    ApiBaseResponse updateStatus(InvoiceUpdateStatusDto request);
}
