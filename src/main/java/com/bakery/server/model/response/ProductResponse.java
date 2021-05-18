package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long price;
    private List<String> images;
    private CategoryResponse category;

    public ProductResponse(Long id, String name, String slug, String description, Long price, String images, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.price = price;
        this.images = Arrays.asList(images.split(","));
        this.category = new CategoryResponse(categoryId, categoryName);
    }
}
