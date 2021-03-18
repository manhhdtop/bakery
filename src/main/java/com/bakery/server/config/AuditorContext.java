package com.bakery.server.config;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
public class AuditorContext {
    private static final String defaultAudit = "SYSTEM";
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
        return userId == null ? 0L : userId;
    }
}
