package com.bakery.server.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum InvoiceStatus {
    INIT(0, "Khởi tạo"),
    CONFIRMED(1, "Đã xác nhận"),
    SHIPPING(2, "Đang giao hàng"),
    SUCCESS(3, "Thành công"),
    REJECT(-1, "Hủy"),
    SHIPPING_ERROR(-2, "Giao hàng thất bại");

    private int status;
    private String name;

    InvoiceStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static InvoiceStatus of(int status) {
        return Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
    }

    public static String getName(int status) {
        InvoiceStatus userStatus = Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
        return userStatus == null ? null : userStatus.name;
    }

    @JsonValue
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
