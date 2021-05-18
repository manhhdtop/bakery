package com.bakery.server.model.response;

import lombok.Data;

@Data
public class OptionTypeResponse {
    private Long id;
    private String name;
    private String description;
    private Integer status;
}
