package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductOptionCreateDto {
    @NotBlank
    private String value;
    @NotNull
    @Valid
    private OptionTypeUpdateDto optionType;
    private String moreInfo;
}
