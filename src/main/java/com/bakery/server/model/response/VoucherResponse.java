package com.bakery.server.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class VoucherResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long value;
    private Long minAmount;
    private Long maxAmount;
    private Integer type;
    private Integer quantity;
    private Date startDate;
    private Date endDate;
    private Integer status;
}
