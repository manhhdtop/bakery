package com.bakery.server.repository.customer.impl;

import com.bakery.server.model.request.ProductRequest;
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
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            sb.append("    (SELECT GROUP_CONCAT(po.id, '|', po.value, '|', po.option_type, '|',(SELECT ot.name FROM option_type ot WHERE po.option_type=ot.id)) \n");
            sb.append("        FROM product_option po WHERE p.id=po.product_id AND po.deleted=0) options, \n");
            sb.append("    (SELECT GROUP_CONCAT(f.uri) FROM file_upload f WHERE f.reference_id=p.id) images \n");
        }
        sb.append("FROM product p, category c \n");
        sb.append("WHERE p.category_id=c.id \n");

        if (StringUtils.isNotBlank(request.getName())) {
            paramsMap.put("productName", request.getName());
            sb.append("    AND p.name LIKE CONCAT('%', :productName, '%') \n");
        }
        if (request.getCategoryId() != null) {
            paramsMap.put("categoryId", request.getCategoryId());
            sb.append("    AND c.id=:categoryId \n");
        }
        sb.append("ORDER BY p.created_date DESC");

        return sb.toString();
    }
}
