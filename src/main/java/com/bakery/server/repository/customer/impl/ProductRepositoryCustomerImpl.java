package com.bakery.server.repository.customer.impl;

import com.bakery.server.model.request.ProductRequest;
import com.bakery.server.model.response.ProductPriceRangeResponse;
import com.bakery.server.model.response.ProductResponse;
import com.bakery.server.repository.customer.ProductRepositoryCustomer;
import com.bakery.server.utils.Constant;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bakery.server.utils.Constant.RESULT_SET_MAPPING.PRODUCT_PRICE_RANGE_RESPONSE;
import static com.bakery.server.utils.Constant.RESULT_SET_MAPPING.PRODUCT_RESPONSE;

@Repository
public class ProductRepositoryCustomerImpl implements ProductRepositoryCustomer {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ProductResponse> getHomeProduct(ProductRequest request) {
        int count = countHomeProduct(request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        if (count == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, count);
        }
        Map<String, Object> paramsMap = new HashMap<>();
        String sql = buildHomeProductSql(request, paramsMap, Constant.SqlType.LIST);
        Query query = entityManager.createNativeQuery(sql, PRODUCT_RESPONSE);
        if (!CollectionUtils.isEmpty(paramsMap)) {
            paramsMap.forEach(query::setParameter);
        }

        Utils.setPage(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    @Override
    public ProductResponse findBySlug(String slug) {
        ProductRequest request = new ProductRequest();
        request.setSlug(slug);
        Map<String, Object> paramsMap = new HashMap<>();
        String sql = buildHomeProductSql(request, paramsMap, Constant.SqlType.LIST);
        Query query = entityManager.createNativeQuery(sql, PRODUCT_RESPONSE);
        if (!CollectionUtils.isEmpty(paramsMap)) {
            paramsMap.forEach(query::setParameter);
        }
        try {
            return (ProductResponse) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ProductPriceRangeResponse calculateProductPriceRange(ProductRequest request) {
        Map<String, Object> paramsMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(request.getName())) {
            paramsMap.put("productName", request.getName());
            sb.append("				AND p.name LIKE CONCAT('%', :productName, '%') \n");
        }
        if (StringUtils.isNotBlank(request.getSlug())) {
            paramsMap.put("slug", request.getSlug());
            sb.append("				AND p.slug=:slug \n");
        }
        if (request.getCategoryId() != null) {
            paramsMap.put("categoryId", request.getCategoryId());
            sb.append("				AND c.id=:categoryId \n");
        }
        if (!CollectionUtils.isEmpty(request.getCategoryIds())) {
            paramsMap.put("categoryIds", request.getCategoryIds());
            sb.append("				AND c.id IN :categoryIds \n");
        }
        if (request.getFromPrice() != null) {
            paramsMap.put("fromPrice", request.getFromPrice());
            sb.append("				AND p.price>=:fromPrice \n");
        }
        if (request.getToPrice() != null) {
            paramsMap.put("toPrice", request.getToPrice());
            sb.append("				AND p.price<=:toPrice \n");
        }
        String condition = sb.toString();
        sb = new StringBuilder();
        sb.append("SELECT  \n");
        sb.append("		( \n");
        sb.append("				SELECT p.price FROM product p \n");
        sb.append("				WHERE p.status=1 \n");
        sb.append(condition);
        sb.append("				ORDER BY p.price LIMIT 1 \n");
        sb.append("    ) min, \n");
        sb.append("    ( \n");
        sb.append("				SELECT p.price FROM product p \n");
        sb.append("				WHERE p.status=1 \n");
        sb.append(condition);
        sb.append("				ORDER BY p.price DESC LIMIT 1 \n");
        sb.append("    ) max \n");

        Query query = entityManager.createNativeQuery(sb.toString(), PRODUCT_PRICE_RANGE_RESPONSE);
        if (!CollectionUtils.isEmpty(paramsMap)) {
            paramsMap.forEach(query::setParameter);
        }
        try {
            return (ProductPriceRangeResponse) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Integer countHomeProduct(ProductRequest request) {
        Map<String, Object> paramsMap = new HashMap<>();
        String sql = buildHomeProductSql(request, paramsMap, Constant.SqlType.COUNTING);
        Query query = entityManager.createNativeQuery(sql);
        if (!CollectionUtils.isEmpty(paramsMap)) {
            paramsMap.forEach(query::setParameter);
        }
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String buildHomeProductSql(ProductRequest request, Map<String, Object> paramsMap, boolean counting) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        StringBuilder sb = new StringBuilder();
        if (counting) {
            sb.append("SELECT COUNT(p.id) \n");
        } else {
            sb.append("SELECT p.id, p.name, p.slug, p.description, p.price, c.id categoryId, c.name categoryName, \n");
            sb.append("    (SELECT GROUP_CONCAT(po.id, '|', po.value, '|', IFNULL(po.price, ''), '|', po.option_type, '|',(SELECT ot.name FROM option_type ot WHERE po.option_type=ot.id)) \n");
            sb.append("        FROM product_option po WHERE p.id=po.product_id AND po.deleted=0) options, \n");
            sb.append("    (SELECT GROUP_CONCAT(f.uri) FROM file_upload f WHERE f.reference_id=p.id) images \n");
        }
        sb.append("FROM product p, category c \n");
        sb.append("WHERE p.category_id=c.id AND p.status=1 \n");

        if (StringUtils.isNotBlank(request.getName())) {
            paramsMap.put("productName", request.getName());
            sb.append("    AND p.name LIKE CONCAT('%', :productName, '%') \n");
        }
        if (StringUtils.isNotBlank(request.getSlug())) {
            paramsMap.put("slug", request.getSlug());
            sb.append("    AND p.slug=:slug \n");
        }
        if (request.getCategoryId() != null) {
            paramsMap.put("categoryId", request.getCategoryId());
            sb.append("    AND c.id=:categoryId \n");
        }
        if (!CollectionUtils.isEmpty(request.getCategoryIds())) {
            paramsMap.put("categoryIds", request.getCategoryIds());
            sb.append("    AND c.id IN :categoryIds \n");
        }
        if (request.getFromPrice() != null) {
            paramsMap.put("fromPrice", request.getFromPrice());
            sb.append("    AND p.price>=:fromPrice \n");
        }
        if (request.getToPrice() != null) {
            paramsMap.put("toPrice", request.getToPrice());
            sb.append("    AND p.price<=:toPrice \n");
        }
        sb.append("ORDER BY p.created_date DESC");

        return sb.toString();
    }
}
