package com.bakery.server.config;

import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<UserEntity> {
    @Autowired
    private AuditorContext auditorContext;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserEntity user = userRepository.findById(userPrincipal.getId()).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        auditorContext.setUser(userPrincipal);

        return Optional.of(user);
    }
}
