package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.entity.ProductEntity;
import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.CategoryRepository;
import com.bakery.server.repository.ProductRepository;
import com.bakery.server.service.CategoryService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(categoryRepository.findAll(pageable));
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        return ApiBaseResponse.success(categoryRepository.findById(id));
    }

    @Override
    public ApiBaseResponse findByName(String name, Pageable pageable) {
        return ApiBaseResponse.success(categoryRepository.findByNameContaining(name.trim(), pageable));
    }

    @Override
    public ApiBaseResponse save(AddCategoryRequest request) {
        request.validData();
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
        request.validData();
        CategoryEntity entity = categoryRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "category.not_found");
        CategoryEntity parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId()).orElse(null);
            AssertUtil.notNull(parent, "category.parent.not_found");
        }
        modelMapper.map(request, entity);
        entity.setParent(parent);
        entity = categoryRepository.save(entity);
        return ApiBaseResponse.success(entity);
    }

    @Override
    public ApiBaseResponse delete(Long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElse(null);
        if (entity != null) {
            List<ProductEntity> products = productRepository.findByCategory(id, Pageable.unpaged()).getContent();
            if (!products.isEmpty()) {
                products.forEach(p -> p.setDeleted(1));
                productRepository.saveAll(products);
            }
            entity.setDeleted(1);
            categoryRepository.save(entity);
        }
        return ApiBaseResponse.success();
    }

    @Override
    public ApiBaseResponse findListActive() {
        return ApiBaseResponse.success(categoryRepository.findByStatus(Status.ACTIVE.getStatus()));
    }

    @Override
    public ApiBaseResponse createSlug(String categoryName) {
        String slug = Utils.createSlug(categoryName);
        return ApiBaseResponse.success(slug);
    }
}
