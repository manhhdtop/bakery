package com.bakery.server.service.impl;

import com.bakery.server.model.request.SearchRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.NewsResponse;
import com.bakery.server.model.response.ProductResponse;
import com.bakery.server.model.response.SearchResponse;
import com.bakery.server.service.NewsService;
import com.bakery.server.service.ProductService;
import com.bakery.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private NewsService newsService;
    @Autowired
    private ProductService productService;

    @Override
    public ApiBaseResponse search(SearchRequest request) {
        request.setKeyword(request.getKeyword().trim());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<NewsResponse> newsEntities = (Page<NewsResponse>) newsService.findByName(request.getKeyword(), pageable).getData();
        Page<ProductResponse> productEntities = (Page<ProductResponse>) productService.findByName(request.getKeyword(), pageable).getData();

        SearchResponse response = SearchResponse.of(newsEntities, productEntities);
        return ApiBaseResponse.success(response);
    }
}
