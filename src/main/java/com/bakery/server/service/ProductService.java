package com.bakery.server.service;

import com.bakery.server.model.request.ProductAddRequest;
import com.bakery.server.model.request.ProductRequest;
import com.bakery.server.model.request.ProductUpdateRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByName(String name, Pageable pageable);

    ApiBaseResponse findByCategory(Long categoryId, Pageable pageable);

    ApiBaseResponse findById(Long id);

    ApiBaseResponse save(ProductAddRequest request);

    ApiBaseResponse update(ProductUpdateRequest request);

    ApiBaseResponse delete(Long id);

    ApiBaseResponse createSlug(String productName);

    ApiBaseResponse getHomeProduct(ProductRequest request);

    ApiBaseResponse findBySlug(String slug);
}
