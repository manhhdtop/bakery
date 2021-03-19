package com.bakery.server.service.impl;

import com.bakery.server.config.UserPrincipal;
import com.bakery.server.entity.UserEntity;
import com.bakery.server.exception.NotFoundException;
import com.bakery.server.repository.UserRepository;
import com.bakery.server.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException(String.format("User with ID: %d not found", id));
        }
        return UserPrincipal.create(user);
    }
}
