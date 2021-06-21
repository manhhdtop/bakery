package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private String password;
    private String email;
    @NotNull
    private Integer status;
    @NotEmpty
    private List<Long> roleIds;
}
