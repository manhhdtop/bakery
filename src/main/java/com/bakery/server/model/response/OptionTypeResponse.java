package com.bakery.server.model.response;

import lombok.Data;

import java.util.List;

@Data
public class OptionTypeResponse {
    private Long id;
    private String name;
    private String description;
    private List<ProductOptionResponse> options;
    private Integer status;
}
