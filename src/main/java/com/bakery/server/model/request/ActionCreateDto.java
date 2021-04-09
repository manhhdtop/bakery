package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ActionCreateDto {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    private Integer status;
}
