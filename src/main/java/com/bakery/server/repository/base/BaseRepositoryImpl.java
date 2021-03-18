package com.bakery.server.repository.base;

import com.bakery.server.entity.base.BaseModel;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    protected final EntityManager entityManager;
    private Class<T> klass;

    BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.klass = entityInformation.getJavaType();
    }

    @Override
    public void delete(Long id) {
        Optional<T> t = findById((ID) id);
        t.ifPresent(e -> {
            ((BaseModel) e).setDeleted(1);
            super.save(e);
        });
    }
}
