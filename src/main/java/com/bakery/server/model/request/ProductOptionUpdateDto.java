package com.bakery.server.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductOptionUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String value;
    private OptionTypeUpdateDto optionType;
    private String moreInfo;
}
