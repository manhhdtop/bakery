package com.bakery.server.repository.customer;

import com.bakery.server.model.response.MenuCategoryResponse;

import java.util.List;

public interface CategoryRepositoryCustomer {
    List<MenuCategoryResponse> getMenuCategories();
}