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
    private Long provinceId;
    private Long districtId;
    private String address;
    private Long voucherId;
    private Integer status;
    private List<CartItemResponse> products;
    private Date createdDate;
    private Long totalAmount;
}
