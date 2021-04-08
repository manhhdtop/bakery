package com.bakery.server.service.impl;

import com.bakery.server.entity.CategoryEntity;
import com.bakery.server.entity.ProductEntity;
import com.bakery.server.model.request.AddProductRequest;
import com.bakery.server.model.request.UpdateProductRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.CategoryRepository;
import com.bakery.server.repository.ProductRepository;
import com.bakery.server.service.ProductService;
import com.bakery.server.utils.AssertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(productRepository.findAll(pageable));
    }

    @Override
    public ApiBaseResponse findByName(String name, Pageable pageable) {
        return ApiBaseResponse.success(productRepository.findByName(name, pageable));
    }

    @Override
    public ApiBaseResponse findByCategory(Long categoryId, Pageable pageable) {
        return ApiBaseResponse.success(productRepository.findByCategory(categoryId, pageable));
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        AssertUtil.notNull(product, "product.not_found");
        return ApiBaseResponse.success(product);
    }

    @Override
    public ApiBaseResponse save(AddProductRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElse(null);
        AssertUtil.notNull(categoryEntity, "category.not_found");
        ProductEntity entity = modelMapper.map(request, ProductEntity.class);
        entity.setCategory(categoryEntity);
        entity = productRepository.save(entity);
        return ApiBaseResponse.success(entity);
    }

    @Override
    public ApiBaseResponse update(UpdateProductRequest request) {
        ProductEntity entity = productRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "category.not_found");
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElse(null);
        AssertUtil.notNull(categoryEntity, "category.not_found");
        entity = modelMapper.map(request, ProductEntity.class);
        entity.setCategory(categoryEntity);
        entity = productRepository.save(entity);
        return ApiBaseResponse.success(entity);
    }

    @Override
    public ApiBaseResponse delete(Long id) {
        ProductEntity entity = productRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(1);
            productRepository.save(entity);
        }
        return ApiBaseResponse.success(null);
    }
}
