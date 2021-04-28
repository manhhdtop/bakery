package com.bakery.server.model.response;

import com.bakery.server.entity.CategoryEntity;
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

    public static CategoryResponse of(CategoryEntity entity) {
        CategoryResponse response = new CategoryResponse();
        response.id = entity.getId();
        response.name = entity.getName();
        response.slug = entity.getSlug();
        response.description = entity.getDescription();
        response.status = entity.getStatus();
        if (entity.getParent() != null) {
            response.parent = CategoryResponse.of(entity.getParent());
        }

        return response;
    }
}
