package com.bakery.server.model.request;

import com.bakery.server.model.response.UploadFileResponse;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewUpdateRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    @NotBlank
    private String description;
    @NotBlank
    private String content;
    @NotNull
    private UploadFileResponse imageUpload;
    private Integer status;

    public void validData() {
    }
}
