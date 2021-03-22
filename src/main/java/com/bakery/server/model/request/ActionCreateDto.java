package com.bakery.server.model.request;

import lombok.Data;

@Data
public class ActionCreateDto {
    private String code;
    private String name;
    private String description;
}
