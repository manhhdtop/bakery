package com.bakery.server.controller;

import com.bakery.server.config.JwtTokenUtil;
import com.bakery.server.exception.UnauthorizedException;
import com.bakery.server.model.request.LoginRequest;
import com.bakery.server.model.response.LoginResponse;
import com.bakery.server.model.response.UserResponse;
import com.bakery.server.service.CustomUserDetailsService;
import com.bakery.server.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthController {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String username = request.getUsername().toLowerCase().trim();
        String password = request.getPassword().toLowerCase().trim();
        UserDetails user = customUserDetailsService.loadUserByUsername(username);
        AssertUtil.notNull(user, "login.error");
        AssertUtil.isTrue(passwordEncoder.matches(password, user.getPassword()), "login.error");
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        String token = jwtTokenUtil.generateAccessToken(user);
        return ResponseEntity.ok().body(LoginResponse.of(userResponse, token));
    }

    @PostMapping("/check-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            throw UnauthorizedException.build();
        }
        token = token.substring(7);
        if (jwtTokenUtil.isExpired(token)) {
            throw UnauthorizedException.build();
        }

        token = jwtTokenUtil.refreshToken(token);
        UserResponse user = jwtTokenUtil.getUser(token);
        return ResponseEntity.ok().body(LoginResponse.of(user, token));
    }
}
