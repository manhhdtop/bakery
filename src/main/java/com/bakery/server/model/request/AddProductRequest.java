package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddProductRequest {
    @NotNull
    private Long categoryId;
}
