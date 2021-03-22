package com.bakery.server.constant;

import java.util.Arrays;

public enum Status {
    DEACTIVE(0, "Chưa kích hoạt"),
    ACTIVE(1, "Hoạt động"),
    LOCK(-1, "Khóa"),
    HIDDEN(-99, "Ẩn");

    private int status;
    private String name;

    Status(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static Status of(int status) {
        return Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
    }

    public static String getName(int status) {
        Status userStatus = Arrays.stream(values()).filter(e -> e.status == status).findFirst().orElse(null);
        return userStatus == null ? null : userStatus.name;
    }

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
