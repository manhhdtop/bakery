package com.bakery.server.repository.customer.impl;

import com.bakery.server.repository.customer.CategoryRepositoryCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CategoryRepositoryCustomerImpl implements CategoryRepositoryCustomer {
    @Autowired
    private EntityManager em;
}
