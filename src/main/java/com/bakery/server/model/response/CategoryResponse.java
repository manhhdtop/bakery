package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer status;
    private CategoryResponse parent;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
