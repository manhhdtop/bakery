package com.bakery.server.controller;

import com.bakery.server.entity.UserEntity;
import com.bakery.server.model.request.LoginRequest;
import com.bakery.server.repository.UserRepository;
import com.bakery.server.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String username = request.getUsername().toLowerCase().trim();
        String password = request.getPassword().toLowerCase().trim();
        UserEntity user = userRepository.findByUsername(username);
        AssertUtil.notNull(user, "login.error");
        AssertUtil.isTrue(passwordEncoder.matches(password, user.getPassword()), "login.error");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
