package com.bakery.server.service;

import com.bakery.server.model.request.SearchRequest;
import com.bakery.server.model.response.ApiBaseResponse;

public interface SearchService {
    ApiBaseResponse search(SearchRequest request);
}
