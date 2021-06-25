package com.bakery.server.repository;

import com.bakery.server.entity.MailTemplateEntity;
import com.bakery.server.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTemplateRepository extends BaseRepository<MailTemplateEntity, Long> {
    MailTemplateEntity findByCode(String code);
}
