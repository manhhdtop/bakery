package com.bakery.server.model.response;

import lombok.Data;

@Data
public class ActionResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer status;
}
