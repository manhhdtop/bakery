package com.bakery.server.constant;

import java.util.Arrays;

public enum RoleCode {
    ROLE_ADMINISTRATOR(1, "ADMINISTRATOR"),
    ROLE_ADMIN(2, "ADMIN"),
    ROLE_CTV(3, "CTV"),
    ROLE_USER(4, "USER");

    private Integer rolePrivilege;
    private String roleCode;

    RoleCode(Integer rolePrivilege, String roleCode) {
        this.rolePrivilege = rolePrivilege;
        this.roleCode = roleCode;
    }

    public static boolean isAuthorize(String currentRoleCode, String targetRoleCode) {
        RoleCode currentRole = Arrays.stream(values()).filter(e -> e.roleCode.equalsIgnoreCase(currentRoleCode)).findFirst().orElse(null);
        RoleCode targetRole = Arrays.stream(values()).filter(e -> e.roleCode.equalsIgnoreCase(currentRoleCode)).findFirst().orElse(null);
        if (currentRole == null || targetRole == null) {
            return false;
        }

        return currentRole.rolePrivilege >= targetRole.rolePrivilege;
    }

    public Integer getRolePrivilege() {
        return rolePrivilege;
    }

    public void setRolePrivilege(Integer rolePrivilege) {
        this.rolePrivilege = rolePrivilege;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
