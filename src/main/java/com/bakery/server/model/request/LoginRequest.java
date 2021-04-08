package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "user.username.can_not_blank")
    private String username;
    @NotBlank(message = "user.password.can_not_blank")
    private String password;
}
