package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ProductRequest {
    private String name;
    private Long categoryId;
    private String categoryName;
    @Min(0)
    private Integer page = 0;
    @Min(5)
    private Integer size = 20;
}
