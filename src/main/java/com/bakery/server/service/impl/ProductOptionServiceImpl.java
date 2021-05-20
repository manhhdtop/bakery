package com.bakery.server.service.impl;

import com.bakery.server.entity.ProductEntity;
import com.bakery.server.entity.ProductOptionEntity;
import com.bakery.server.model.request.ProductOptionCreateDto;
import com.bakery.server.model.request.ProductOptionUpdateDto;
import com.bakery.server.repository.ProductOptionRepository;
import com.bakery.server.service.ProductOptionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductOptionEntity> saveAll(List<ProductOptionCreateDto> productOptionCreateDtos, ProductEntity productEntity) {
        if (CollectionUtils.isEmpty(productOptionCreateDtos)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ProductOptionEntity>>() {
        }.getType();
        List<ProductOptionEntity> entities = modelMapper.map(productOptionCreateDtos, type);

        entities = productOptionRepository.saveAll(entities);
        return entities;
    }

    @Override
    public List<ProductOptionEntity> updateAll(List<ProductOptionUpdateDto> productOptionUpdateDtos, ProductEntity productEntity) {
        if (CollectionUtils.isEmpty(productOptionUpdateDtos)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ProductOptionEntity>>() {
        }.getType();
        List<ProductOptionEntity> entities = modelMapper.map(productOptionUpdateDtos, type);
        List<Long> ids = productOptionUpdateDtos.stream().map(ProductOptionUpdateDto::getId).collect(Collectors.toList());
        List<ProductOptionEntity> entitiesOld = productOptionRepository.findByProductId(productEntity.getId());
        List<ProductOptionEntity> entitiesToDelete = entitiesOld.stream().filter(e -> !ids.contains(e.getId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(entitiesToDelete)) {
            entitiesToDelete.forEach(e -> e.setDeleted(1));
            productOptionRepository.saveAll(entitiesToDelete);
        }
        entities = productOptionRepository.saveAll(entities);
        return entities;
    }
}
