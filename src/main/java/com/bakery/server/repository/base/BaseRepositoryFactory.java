package com.bakery.server.repository.base;

import com.bakery.server.utils.AssertUtil;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
    private final EntityManager entityManager;

    public BaseRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(information.getDomainType());
        Object repository = this.getTargetRepositoryViaReflection(information, new Object[]{entityInformation, entityManager});
        AssertUtil.isInstanceOf(BaseRepositoryImpl.class, repository);
        return (JpaRepositoryImplementation) repository;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return BaseRepositoryImpl.class;
    }
}