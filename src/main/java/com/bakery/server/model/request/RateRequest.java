package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RateRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Integer rate;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
    private String email;
    @NotBlank
    private String description;
}
