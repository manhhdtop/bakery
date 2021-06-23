package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InvoiceCreateDto {
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private String customerPhone;
    @NotNull
    private Long provinceId;
    @NotNull
    private Long districtId;
    @NotBlank
    private String address;
    private String voucherCode;
    private List<CartItemRequest> products;

    public void validate() {
        customerName = customerName.trim();
        customerEmail = customerEmail.trim();
        customerPhone = customerPhone.trim();
        address = address.trim();
        if (voucherCode != null) {
            voucherCode = voucherCode.trim();
        }
    }
}
