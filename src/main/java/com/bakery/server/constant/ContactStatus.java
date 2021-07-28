package com.bakery.server.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ContactStatus {
    INIT(0, "Khởi tạo"),
    SUCCESS(1, "Đã xử lý"),
    REJECT(2, "Từ chối");

    private int status;
    private String name;

    ContactStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static ContactStatus of(int status) {
        return Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
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
