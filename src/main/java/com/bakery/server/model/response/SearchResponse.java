package com.bakery.server.model.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class SearchResponse {
    private Page<NewsResponse> news;
    private Page<ProductResponse> products;

    public static SearchResponse of(Page<NewsResponse> news, Page<ProductResponse> products) {
        SearchResponse response = new SearchResponse();
        response.news = news;
        response.products = products;
        return response;
    }
}
