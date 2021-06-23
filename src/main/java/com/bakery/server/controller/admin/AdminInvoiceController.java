package com.bakery.server.controller.admin;

import com.bakery.server.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RequestMapping("${admin-base-path}/invoice")
@RestController
public class AdminInvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<?> search(@NotBlank @RequestParam("customerPhone") String phoneNumber,
                                    @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(invoiceService.findAll(phoneNumber, pageable));
    }
}
