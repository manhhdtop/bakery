package com.bakery.server.controller;

import com.bakery.server.model.request.InvoiceCreateDto;
import com.bakery.server.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("invoice")
@RestController
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create-invoice")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceCreateDto invoiceCreateDto) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceCreateDto));
    }
}
