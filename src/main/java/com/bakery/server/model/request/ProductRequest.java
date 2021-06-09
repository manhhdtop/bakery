package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private String slug;
    private Long categoryId;
    private String categoryName;
    private List<Long> categoryIds;
    private Long fromPrice;
    private Long toPrice;
    @Min(0)
    private Integer page = 0;
    @Min(5)
    private Integer size = 20;
}
