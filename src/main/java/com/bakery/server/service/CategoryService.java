package com.bakery.server.service;

import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findById(Long id);

    ApiBaseResponse findByName(String name, Pageable pageable);

    ApiBaseResponse save(AddCategoryRequest request);

    ApiBaseResponse findListParent();

    ApiBaseResponse update(UpdateCategoryRequest request);

    ApiBaseResponse delete(Long id);

    ApiBaseResponse findListActive();

    ApiBaseResponse createSlug(String categoryName);
}
