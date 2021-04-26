package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String description;
    @NotNull
    private Integer status;
    private Long parentId;

    public void validData() {
        name = name.trim();
        slug = slug.trim();
        description = description.trim();
    }
}
