package com.bakery.server.service.impl;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.entity.ProductEntity;
import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.CategoryResponse;
import com.bakery.server.model.response.MenuCategoryResponse;
import com.bakery.server.repository.CategoryRepository;
import com.bakery.server.repository.ProductRepository;
import com.bakery.server.service.CategoryService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        return ApiBaseResponse.success(convertPage(categoryRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        return ApiBaseResponse.success(convert(categoryRepository.findById(id).orElse(null)));
    }

    @Override
    public ApiBaseResponse findByName(String name, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(categoryRepository.findByNameContaining(name.trim(), pageable)));
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
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse findListParent() {
        return ApiBaseResponse.success(convertList(categoryRepository.findByParentIdIsNull()));
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
        return ApiBaseResponse.success(convert(entity));
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
        List<CategoryEntity> categories = categoryRepository.findByStatus(Status.ACTIVE.getStatus());
        return ApiBaseResponse.success(convertList(categories));
    }

    @Override
    public ApiBaseResponse createSlug(String categoryName) {
        String slug = Utils.createSlug(categoryName);
        return ApiBaseResponse.success(slug);
    }

    @Override
    public ApiBaseResponse findBySlug(String slug) {
        CategoryEntity categoryEntity = categoryRepository.findBySlug(slug.trim().toLowerCase());
        AssertUtil.notNull(categoryEntity, "category.not_found");
        AssertUtil.isTrue(categoryEntity.getStatus().equals(Status.ACTIVE.getStatus()), "category.not_found");
        return ApiBaseResponse.success(convert(categoryEntity));
    }

    @Override
    public ApiBaseResponse getMenuCategories() {
        Type type = new TypeToken<List<MenuCategoryResponse>>() {
        }.getType();
        return ApiBaseResponse.success(modelMapper.map(categoryRepository.getMenuCategories(), type));
    }

    private CategoryResponse convert(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return modelMapper.map(categoryEntity, CategoryResponse.class);
    }

    private List<CategoryResponse> convertList(List<CategoryEntity> categoryEntities) {
        if (CollectionUtils.isEmpty(categoryEntities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<CategoryResponse>>() {
        }.getType();
        return modelMapper.map(categoryEntities, type);
    }

    private Page<CategoryResponse> convertPage(Page<CategoryEntity> page) {
        List<CategoryEntity> categoryEntities = page.getContent();
        return new PageImpl<>(convertList(categoryEntities), page.getPageable(), page.getTotalElements());
    }
}
