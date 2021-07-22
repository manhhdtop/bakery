package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class SearchRequest {
    @NotBlank
    private String keyword;
    @Min(1)
    private Integer page = 1;
    @Min(5)
    private Integer size = 10;
}
