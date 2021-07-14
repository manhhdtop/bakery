package com.bakery.server.service;

import com.bakery.server.model.request.NewUpdateRequest;
import com.bakery.server.model.request.NewsAddRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    ApiBaseResponse findAll(Pageable pageable);

    ApiBaseResponse findByName(String name, Pageable pageable);

    ApiBaseResponse findById(Long id);

    ApiBaseResponse save(NewsAddRequest request);

    ApiBaseResponse update(NewUpdateRequest request);

    ApiBaseResponse delete(Long id);

    ApiBaseResponse createSlug(String name);

    ApiBaseResponse findBySlug(String slug);

    ApiBaseResponse readNews(Long id);

    ApiBaseResponse likeNews(Long id);

    ApiBaseResponse getHomeNews();
}
