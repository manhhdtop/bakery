package com.bakery.server.model.request;

import com.bakery.server.constant.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddCategoryRequest {
    @NotBlank
    private String name;
    private String description;
    private Long parentId;
    @NotNull
    private Status status;
}
