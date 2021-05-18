package com.bakery.server.model.response;

import lombok.Data;

import java.util.List;

@Data
public class RoleResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer status;
    private List<ActionResponse> actions;
}
