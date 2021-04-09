package com.bakery.server.repository;

import com.bakery.server.entity.UserEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.username LIKE '%:keyword%' OR u.name LIKE '%:keyword%'")
    Page<UserEntity> findByUsernameContainingOrNameContaining(String keyword, Pageable pageable);
}
