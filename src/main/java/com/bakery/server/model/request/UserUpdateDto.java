package com.bakery.server.model.request;

import com.bakery.server.constant.UserStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    private Long id;
    private String name;
    private String email;
    private UserStatus status;
    private List<Long> roles;
}
