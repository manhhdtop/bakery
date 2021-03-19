package com.bakery.server.controller;

import com.bakery.server.config.JwtTokenUtil;
import com.bakery.server.model.request.LoginRequest;
import com.bakery.server.model.response.UserResponse;
import com.bakery.server.service.CustomUserDetailsService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String username = request.getUsername().toLowerCase().trim();
        String password = request.getPassword().toLowerCase().trim();
        UserDetails user = customUserDetailsService.loadUserByUsername(username);
        AssertUtil.notNull(user, "login.error");
        AssertUtil.isTrue(passwordEncoder.matches(password, user.getPassword()), "login.error");
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
                .body(userResponse);
    }
}
