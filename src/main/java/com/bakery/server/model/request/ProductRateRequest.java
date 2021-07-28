package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductRateRequest {
    @NotNull
    private Long productId;
    @Min(0)
    private Integer page = 0;
    @Min(5)
    private Integer size = 20;
}
