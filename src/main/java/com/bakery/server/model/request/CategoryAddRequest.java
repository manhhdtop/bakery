package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryAddRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String description;
    private Long parentId;
    @NotNull
    private Integer status;

    public void validData() {
        name = name.trim();
        slug = slug.trim();
        description = description.trim();
    }
}
