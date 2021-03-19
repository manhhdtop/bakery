package com.bakery.server.model.request;

import lombok.Data;

@Data
public class RoleUpdateDto {
    private Long id;
    private String code;
    private String name;
}
