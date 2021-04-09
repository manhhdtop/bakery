package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ActionUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    private Integer status;
}
