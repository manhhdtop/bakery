package com.bakery.server.model.response;

import lombok.Data;

@Data
public class OptionResponse {
    private Long id;
    private String value;
    private OptionTypeResponse optionType;
    private String moreInfo;
}
