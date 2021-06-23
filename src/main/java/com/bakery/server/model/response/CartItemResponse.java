package com.bakery.server.model.response;

import lombok.Data;

@Data
public class CartItemResponse {
    private ProductResponse product;
    private Integer quantity;
}
