package com.bakery.server.service;

import com.bakery.server.model.request.MailTemplateCreateDto;
import com.bakery.server.model.request.MailTemplateUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;

public interface MailTemplateService {
    ApiBaseResponse findAll(String code, Pageable pageable);

    ApiBaseResponse save(MailTemplateCreateDto request);

    ApiBaseResponse update(MailTemplateUpdateDto request);

    void delete(Long id);
}
