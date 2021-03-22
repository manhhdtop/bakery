package com.bakery.server.model.request;

import lombok.Data;

@Data
public class ActionUpdateDto {
    private Long id;
    private String code;
    private String name;
    private String description;
}
