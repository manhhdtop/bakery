package com.bakery.server.service;

import com.bakery.server.entity.ProductEntity;
import com.bakery.server.entity.ProductOptionEntity;
import com.bakery.server.model.request.ProductOptionCreateDto;
import com.bakery.server.model.request.ProductOptionUpdateDto;

import java.util.List;

public interface ProductOptionService {
    List<ProductOptionEntity> saveAll(List<ProductOptionCreateDto> productOptionCreateDtos, ProductEntity productEntity);

    List<ProductOptionEntity> updateAll(List<ProductOptionUpdateDto> productOptionUpdateDtos, ProductEntity productEntity);
}
