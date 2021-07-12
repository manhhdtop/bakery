package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewContactDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String content;
}
