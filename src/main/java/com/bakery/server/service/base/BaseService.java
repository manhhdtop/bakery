package com.bakery.server.service.base;

import com.bakery.server.repository.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class BaseService<T, ID extends Serializable, R extends BaseRepository<T, ID>> {
    @Autowired
    private R r;

    public T save(T t) {
        return (T) r.save(t);
    }

    public List<T> saveAll(List<T> t) {
        return r.saveAll(t);
    }

    public T update(T t) {
        return (T) r.save(t);
    }

    public void delete(Long id) {
        r.delete(id);
    }
}
