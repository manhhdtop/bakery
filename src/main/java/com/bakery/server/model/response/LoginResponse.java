package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LoginResponse implements Serializable {
    private UserResponse user;
    private String token;

    public static LoginResponse of(UserResponse user, String token) {
        return LoginResponse.builder()
                .user(user)
                .token(token)
                .build();
    }
}
