package com.bakery.server.service.impl;

import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.repository.CatalogRepository;
import com.bakery.server.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public ApiBaseResponse getListProvince() {
        return ApiBaseResponse.success(catalogRepository.getListProvince());
    }

    @Override
    public ApiBaseResponse getListDistrict(Long provinceId) {
        return ApiBaseResponse.success(catalogRepository.getListDistrict(provinceId));
    }
}
