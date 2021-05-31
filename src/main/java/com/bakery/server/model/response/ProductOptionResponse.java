package com.bakery.server.model.response;

import com.bakery.server.entity.base.AuditModel;
import lombok.Data;

@Data
public class ProductOptionResponse {
    private Long id;
    private Long productId;
    private String value;
    private OptionTypeResponse optionType;
    private String moreInfo;
}
