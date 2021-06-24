package com.bakery.server.model.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceResponse {
    private Long id;
    private String invoiceId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private CategoryResponse province;
    private CategoryResponse district;
    private String address;
    private VoucherResponse voucher;
    private Integer status;
    private String statusDescription;
    private List<CartItemResponse> products;
    private Date createdDate;
    private Long totalAmount;
}
