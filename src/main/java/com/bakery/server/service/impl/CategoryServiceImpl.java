package com.bakery.server.service.impl;

import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.CategoryRepository;
import com.bakery.server.service.CategoryService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiBaseResponse findAll() {
        return ApiBaseResponse.success(categoryRepository.findAll());
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        return ApiBaseResponse.success(categoryRepository.findById(id));
    }

    @Override
    public ApiBaseResponse findByName(String name) {
        return ApiBaseResponse.success(categoryRepository.findByNameContaining(name.trim()));
    }

    @Override
    public ApiBaseResponse save(AddCategoryRequest request) {
        CategoryEntity parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId()).orElse(null);
            AssertUtil.notNull(parent, "category.parent.not_found");
        }
        CategoryEntity entity = modelMapper.map(request, CategoryEntity.class);
        entity.setParent(parent);
        entity = categoryRepository.save(entity);
        return ApiBaseResponse.success(entity);
    }

    @Override
    public ApiBaseResponse findListParent() {
        return ApiBaseResponse.success(categoryRepository.findByParentIdIsNull());
    }

    @Override
    public ApiBaseResponse update(UpdateCategoryRequest request) {
        CategoryEntity entity = categoryRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "category.not_found");
        CategoryEntity parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId()).orElse(null);
            AssertUtil.notNull(parent, "category.parent.not_found");
        }
        entity = modelMapper.map(request, CategoryEntity.class);
        entity.setParent(parent);
        categoryRepository.save(entity);
        return ApiBaseResponse.success(entity);
    }

    @Override
    public ApiBaseResponse delete(Long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(1);
            categoryRepository.save(entity);
        }
        return ApiBaseResponse.success(null);
    }
}
