package com.bakery.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {
    @Autowired
    private AuditorContext auditorContext;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of(auditorContext.getCurrentUserId());
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        auditorContext.setUserId(userPrincipal.getId());
        auditorContext.setUsername(userPrincipal.getUsername());
        auditorContext.setName(userPrincipal.getName());

        return Optional.ofNullable(userPrincipal.getId());
    }
}
