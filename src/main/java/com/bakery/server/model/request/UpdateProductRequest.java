package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateProductRequest {
    @NotNull
    private Long id;
    @NotNull
    private Long categoryId;
}
