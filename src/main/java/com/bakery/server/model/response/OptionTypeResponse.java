package com.bakery.server.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OptionTypeResponse {
    private Long id;
    private String name;
    private String description;
    private List<ProductOptionResponse> options;
    private Integer status;

    public OptionTypeResponse(Long id, String name) {
        this.id = id;
        this.name= name;
        this.options = new ArrayList<>();
        this.status = 1;
    }
}
