package com.bakery.server.config;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class AuditorContext {
    private static final String defaultAudit = "SYSTEM";
    private static final Long defaultAuditId = 0L;
    private String name;
    private Long userId;
    private String username;

    public String getCurrentName() {
        return name == null ? defaultAudit : name;
    }

    public String getCurrentUsername() {
        return username == null ? defaultAudit : username;
    }

    public Long getCurrentUserId() {
        return userId == null ? defaultAuditId : userId;
    }

    public void setUser(UserPrincipal userDetails) {
        userId = userDetails.getId();
        username = userDetails.getUsername();
        name = userDetails.getName();
    }
}
