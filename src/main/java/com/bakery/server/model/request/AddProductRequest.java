package com.bakery.server.model.request;

import com.bakery.server.model.response.UploadFileResponse;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddProductRequest {
    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<UploadFileResponse> imageUploads;
    @NotNull
    private Long categoryId;
    @NotNull
    private Integer status;
}
