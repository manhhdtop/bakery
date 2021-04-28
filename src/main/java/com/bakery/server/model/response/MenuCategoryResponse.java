package com.bakery.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MenuCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    List<MenuCategoryResponse> childs;

    public MenuCategoryResponse(Long id, String name, String slug, String description) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
    }

    public void addChild(MenuCategoryResponse child) {
        if (this.childs == null) {
            this.childs = new ArrayList<>();
        }
        this.childs.add(child);
    }
}
