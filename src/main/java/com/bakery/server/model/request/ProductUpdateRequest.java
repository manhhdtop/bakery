package com.bakery.server.model.request;

import com.bakery.server.model.response.UploadFileResponse;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductUpdateRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String description;
    @NotNull
    private Long price;
    @NotEmpty
    private List<UploadFileResponse> imageUploads;
    @NotNull
    private Long categoryId;
    @NotNull
    private Integer status;
    private List<ProductOptionUpdateDto> productOptions;

    public void validData() {
        name = name.trim();
        slug = slug.trim();
        description = description.trim();
    }
}
