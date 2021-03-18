package com.bakery.server.repository;

import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
