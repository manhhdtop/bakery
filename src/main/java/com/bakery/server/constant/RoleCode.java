package com.bakery.server.constant;

public enum RoleCode {
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private String roleCode;

    RoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
