package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OptionCreateDto {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer status;
}
