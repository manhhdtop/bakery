package com.bakery.server.service.impl;

import com.bakery.server.constant.ReferenceType;
import com.bakery.server.entity.*;
import com.bakery.server.model.request.*;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.ProductPriceRangeResponse;
import com.bakery.server.model.response.ProductResponse;
import com.bakery.server.model.response.UploadFileResponse;
import com.bakery.server.repository.CategoryRepository;
import com.bakery.server.repository.FileUploadRepository;
import com.bakery.server.repository.ProductRateRepository;
import com.bakery.server.repository.ProductRepository;
import com.bakery.server.service.ProductOptionService;
import com.bakery.server.service.ProductService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileUploadRepository fileUploadRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductOptionService productOptionService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRateRepository productRateRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(productRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByName(String name, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(productRepository.findByNameContaining(name, pageable)));
    }

    @Override
    public ApiBaseResponse findByCategory(Long categoryId, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(productRepository.findByCategory(categoryId, pageable)));
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        AssertUtil.notNull(product, "product.not_found");
        return ApiBaseResponse.success(convert(product));
    }

    @Override
    public ApiBaseResponse save(ProductAddRequest request) {
        request.validData();
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElse(null);
        AssertUtil.notNull(categoryEntity, "category.not_found");
        ProductEntity entity = modelMapper.map(request, ProductEntity.class);
        entity.setCategory(categoryEntity);

        entity = productRepository.save(entity);

        if (!CollectionUtils.isEmpty(request.getProductOptions())) {
            List<ProductOptionEntity> productOptionEntities = productOptionService.saveAll(request.getProductOptions(), entity);
            entity.setOptions(productOptionEntities);
        }

        Type fileUploadEntitiesType = new TypeToken<List<FileUploadEntity>>() {
        }.getType();
        List<FileUploadEntity> fileUploadEntities = modelMapper.map(request.getImageUploads(), fileUploadEntitiesType);
        Long referenceId = entity.getId();
        fileUploadEntities.forEach(e -> {
            e.setReferenceId(referenceId);
            e.setReferenceType(ReferenceType.PRODUCT.getName());
        });
        fileUploadRepository.saveAll(fileUploadEntities);
        entity.setImages(fileUploadEntities);
        entity = productRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse update(ProductUpdateRequest request) {
        request.validData();
        ProductEntity entity = productRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "category.not_found");
        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElse(null);
        AssertUtil.notNull(categoryEntity, "category.not_found");
        modelMapper.map(request, entity);
        entity.setCategory(categoryEntity);
        Type fileUploadEntitiesType = new TypeToken<List<FileUploadEntity>>() {
        }.getType();
        List<FileUploadEntity> fileUploadEntities = modelMapper.map(request.getImageUploads(), fileUploadEntitiesType);
        Long referenceId = entity.getId();
        fileUploadEntities.forEach(e -> {
            e.setReferenceId(referenceId);
            e.setReferenceType(ReferenceType.PRODUCT.getName());
        });
        if (!CollectionUtils.isEmpty(fileUploadEntities)) {
            fileUploadRepository.saveAll(fileUploadEntities);
        }

        if (!CollectionUtils.isEmpty(request.getProductOptions())) {
            List<ProductOptionEntity> productOptionEntities = productOptionService.updateAll(request.getProductOptions(), entity);
            entity.setOptions(productOptionEntities);
        }

        List<Long> idOlds = request.getImageUploads().stream().map(UploadFileResponse::getId).filter(Objects::nonNull).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idOlds)) {
            List<FileUploadEntity> byReferenceId = fileUploadRepository.findByReferenceId(request.getId());
            if (!CollectionUtils.isEmpty(idOlds)) {
                byReferenceId = byReferenceId.stream().filter(e -> idOlds.contains(e.getId())).collect(Collectors.toList());
                List<FileUploadEntity> deletedFileUploadEntity = byReferenceId.stream().filter(e -> !idOlds.contains(e.getId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idOlds)) {
                    deletedFileUploadEntity.forEach(e -> e.setDeleted(1));
                    fileUploadRepository.saveAll(deletedFileUploadEntity);
                }
            }
        }
        entity.setImages(fileUploadEntities);

        entity = productRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse delete(Long id) {
        ProductEntity entity = productRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(1);
            productRepository.save(entity);
        }
        return ApiBaseResponse.success();
    }

    @Override
    public ApiBaseResponse createSlug(String productName) {
        String slug = Utils.createSlug(productName);
        ProductEntity entityBySlug = productRepository.findBySlugLike(slug);
        if (entityBySlug != null) {
            String lastSlug = entityBySlug.getSlug();
            slug = Utils.createNewSlug(slug, lastSlug);
        }
        return ApiBaseResponse.success(slug);
    }

    @Override
    public ApiBaseResponse getHomeProduct(ProductRequest request) {
        if (request.getFromPrice() != null && request.getToPrice() != null) {
            AssertUtil.isTrue(request.getToPrice() >= request.getFromPrice(), "product.search.from_price_must_less_than_to_price");
        }
        ProductPriceRangeResponse productPriceRangeResponse = productRepository.calculateProductPriceRange(request);
        return ApiBaseResponse.success(productRepository.getHomeProduct(request), productPriceRangeResponse);
    }

    @Override
    public ApiBaseResponse findBySlug(String slug) {
        ProductResponse response = productRepository.findBySlug(slug);
        AssertUtil.notNull(response, "product.not_found");
        return ApiBaseResponse.success(response);
    }

    @Override
    public ApiBaseResponse rate(RateRequest request) {
        AssertUtil.notNull(request.getProductId(), "product.not_found");
        AssertUtil.notNull(request.getRate(), "product.rate.value_invalid");
        ProductEntity entity = productRepository.findById(request.getProductId()).orElse(null);
        AssertUtil.notNull(entity, "product.not_found");
        if (StringUtils.isNotBlank(request.getDescription())) {
            request.setDescription(request.getDescription().trim());
        }
        ProductRateEntity rate = modelMapper.map(request, ProductRateEntity.class);
        productRateRepository.save(rate);
        ProductResponse response = productRepository.findBySlug(entity.getSlug());
        return ApiBaseResponse.success(response);
    }

    @Override
    public ApiBaseResponse getRates(ProductRateRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return ApiBaseResponse.success(productRateRepository.findByProductIdOrderByCreatedDateDesc(request.getProductId(), pageable));
    }

    private ProductResponse convert(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        return modelMapper.map(productEntity, ProductResponse.class);
    }

    private List<ProductResponse> convertList(List<ProductEntity> productEntities) {
        if (CollectionUtils.isEmpty(productEntities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ProductResponse>>() {
        }.getType();
        return modelMapper.map(productEntities, type);
    }

    private Page<ProductResponse> convertPage(Page<ProductEntity> page) {
        List<ProductEntity> productEntities = page.getContent();
        return new PageImpl<>(convertList(productEntities), page.getPageable(), page.getTotalElements());
    }
}
