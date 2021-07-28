package com.bakery.server.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ReferenceType {
    PRODUCT("PRODUCT"),
    NEWS("NEWS");

    private String name;

    ReferenceType(String name) {
        this.name = name;
    }

    public static ReferenceType of(String name) {
        return Arrays.stream(values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public static String getName(String name) {
        ReferenceType userStatus = Arrays.stream(values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
        return userStatus == null ? null : userStatus.name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
