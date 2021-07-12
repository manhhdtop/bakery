package com.bakery.server.service;

import com.bakery.server.model.request.ContactUpdateStatusDto;
import com.bakery.server.model.request.NewContactDto;
import com.bakery.server.model.response.ApiBaseResponse;
import org.springframework.data.domain.Pageable;


public interface ContactService {
    ApiBaseResponse findAll(String keyword, Pageable pageable);

    ApiBaseResponse newContact(NewContactDto request);

    ApiBaseResponse updateStatus(ContactUpdateStatusDto request);
}
