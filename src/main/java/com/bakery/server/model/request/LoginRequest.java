package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "{login.username.canNotBlank}")
    private String username;
    @NotBlank(message = "${login.password.canNotBlank}")
    private String password;
}
