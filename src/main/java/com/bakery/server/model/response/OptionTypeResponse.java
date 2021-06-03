package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OptionTypeResponse {
    private Long id;
    private String name;
    private String description;
    private List<ProductOptionResponse> options;
    private Integer status;

    public OptionTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
        this.options = new ArrayList<>();
        this.status = 1;
    }
}
