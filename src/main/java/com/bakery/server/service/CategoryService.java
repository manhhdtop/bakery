package com.bakery.server.service;

import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.model.response.ApiBaseResponse;

public interface CategoryService {

    ApiBaseResponse findAll();

    ApiBaseResponse findById(Long id);

    ApiBaseResponse findByName(String name);

    ApiBaseResponse save(AddCategoryRequest request);

    ApiBaseResponse findListParent();

    ApiBaseResponse update(UpdateCategoryRequest request);

    ApiBaseResponse delete(Long id);
}
