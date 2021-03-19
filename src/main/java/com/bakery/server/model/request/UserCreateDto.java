package com.bakery.server.model.request;

import com.bakery.server.constant.UserStatus;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
public class UserCreateDto {
    private String username;
    private String name;
    private String password;
    private String email;
    private UserStatus status;
    private List<Long> roles;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
