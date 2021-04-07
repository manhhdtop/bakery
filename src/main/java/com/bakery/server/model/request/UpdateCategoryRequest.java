package com.bakery.server.model.request;

import com.bakery.server.constant.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Status status;
    private Long parentId;
}
