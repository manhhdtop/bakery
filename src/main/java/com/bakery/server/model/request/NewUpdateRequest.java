package com.bakery.server.model.request;

import lombok.Data;

@Data
public class NewUpdateRequest {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String content;
    private Integer status;

    public void validData() {
    }
}
