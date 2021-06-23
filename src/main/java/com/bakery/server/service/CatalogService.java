package com.bakery.server.service;

import com.bakery.server.model.response.ApiBaseResponse;

public interface CatalogService {

    ApiBaseResponse getListProvince();

    ApiBaseResponse getListDistrict(Long provinceId);
}
