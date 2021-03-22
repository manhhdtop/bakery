package com.bakery.server.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<String> getRoles() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return simpleGrantedAuthorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
