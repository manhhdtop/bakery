package com.bakery.server.repository.customer.impl;

import com.bakery.server.model.response.MenuCategoryResponse;
import com.bakery.server.repository.customer.CategoryRepositoryCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepositoryCustomerImpl implements CategoryRepositoryCustomer {
    @Autowired
    private EntityManager em;

    @Override
    public List<MenuCategoryResponse> getMenuCategories() {
        Map<Long, MenuCategoryResponse> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.id parentId, p.name parentName, p.slug parentSlug, p.description parentDescription, \n");
        sb.append("	c.id childId, c.name childName, c.slug childSlug, c.description childDescription \n");
        sb.append("FROM category p LEFT JOIN category c ON p.id=c.parent_id AND c.status=1 \n");
        sb.append("WHERE p.status=1 AND p.parent_id IS NULL");

        Query query = em.createNativeQuery(sb.toString());

        List<Object[]> results = query.getResultList();
        if (!CollectionUtils.isEmpty(results)) {
            Long id;
            String name;
            String slug;
            String description;
            for (Object[] item : results) {
                int i = 0;
                id = ((BigInteger) item[i++]).longValue();
                MenuCategoryResponse menuParent = map.get(id);
                if (menuParent == null) {
                    name = String.valueOf(item[i++]);
                    slug = String.valueOf(item[i++]);
                    description = String.valueOf(item[i++]);
                    menuParent = new MenuCategoryResponse(id, name, slug, description);
                    map.put(id, menuParent);
                } else {
                    i += 3;
                }

                if (item[i] != null) {
                    id = ((BigInteger) item[i++]).longValue();
                    name = String.valueOf(item[i++]);
                    slug = String.valueOf(item[i++]);
                    description = String.valueOf(item[i]);
                    menuParent.addChild(new MenuCategoryResponse(id, name, slug, description));
                }
            }
        }
        return new ArrayList<>(map.values());
    }
}
