package com.bakery.server.model.request;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserCreateDto {
    @NotBlank(message = "{user.username.can_not_blank}")
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private String email;
    private Integer status;
    @NotEmpty
    private List<Long> roleIds;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
