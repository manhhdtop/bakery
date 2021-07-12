package com.bakery.server.repository;

import com.bakery.server.entity.ContactEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends BaseRepository<ContactEntity, Long> {
    @Query("SELECT c FROM ContactEntity c WHERE c.name LIKE '%:keyword%' OR c.phoneNumber=:keyword OR c.email=:keyword")
    Page<ContactEntity> search(String keyword, Pageable pageable);
}
