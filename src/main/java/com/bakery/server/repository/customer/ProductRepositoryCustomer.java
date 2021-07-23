package com.bakery.server.repository.customer;

import com.bakery.server.model.request.ProductRequest;
import com.bakery.server.model.response.ProductPriceRangeResponse;
import com.bakery.server.model.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustomer {
    Page<ProductResponse> getHomeProduct(ProductRequest request);

    ProductResponse findBySlug(String slug);

    ProductPriceRangeResponse calculateProductPriceRange(ProductRequest request);
}
