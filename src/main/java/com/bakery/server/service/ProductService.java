package com.bakery.server.service;

import com.bakery.server.model.request.AddProductRequest;
import com.bakery.server.model.request.UpdateProductRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByName(String name, Pageable pageable);

    ApiBaseResponse findByCategory(Long categoryId, Pageable pageable);

    ApiBaseResponse findById(Long id);

    ApiBaseResponse save(AddProductRequest request);

    ApiBaseResponse update(UpdateProductRequest request);

    ApiBaseResponse delete(Long id);
}