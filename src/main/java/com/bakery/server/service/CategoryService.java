package com.bakery.server.service;

import com.bakery.server.model.request.CategoryAddRequest;
import com.bakery.server.model.request.CategoryUpdateRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findById(Long id);

    ApiBaseResponse findByName(String name, Pageable pageable);

    ApiBaseResponse save(CategoryAddRequest request);

    ApiBaseResponse findListParent();

    ApiBaseResponse update(CategoryUpdateRequest request);

    ApiBaseResponse delete(Long id);

    ApiBaseResponse findListActive();

    ApiBaseResponse createSlug(String categoryName);

    ApiBaseResponse findBySlug(String slug);

    ApiBaseResponse getMenuCategories();
}
