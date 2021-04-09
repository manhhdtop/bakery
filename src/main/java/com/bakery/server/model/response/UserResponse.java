package com.bakery.server.model.response;

import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.RoleEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponse {
    private String username;
    private Long id;
    private String name;
    private String email;
    private Integer status;
    private Date createdDate;
    private Date updatedDate;
    private List<RoleEntity> roles;
}
