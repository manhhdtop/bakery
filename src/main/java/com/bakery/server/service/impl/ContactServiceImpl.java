package com.bakery.server.service.impl;

import com.bakery.server.constant.ContactStatus;
import com.bakery.server.entity.ContactEntity;
import com.bakery.server.model.request.ContactUpdateStatusDto;
import com.bakery.server.model.request.NewContactDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.ContactResponse;
import com.bakery.server.repository.ContactRepository;
import com.bakery.server.service.ContactService;
import com.bakery.server.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiBaseResponse findAll(String keyword, Pageable pageable) {
        Page<ContactEntity> page;
        if (StringUtils.isBlank(keyword)) {
            page = contactRepository.findAll(pageable);
        } else {
            page = contactRepository.search(keyword.trim(), pageable);
        }

        return ApiBaseResponse.success(convertPage(page, pageable));
    }

    @Override
    public ApiBaseResponse newContact(NewContactDto request) {
        ContactEntity entity = modelMapper.map(request, ContactEntity.class);
        entity.setStatus(ContactStatus.INIT.getStatus());
        entity = contactRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse updateStatus(ContactUpdateStatusDto request) {
        request.validate();
        ContactEntity entity = contactRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "contact.not_exist");
        entity.setStatus(request.getStatus());
        entity.setStatusDescription(request.getDescription());
        entity = contactRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    private ContactResponse convert(ContactEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, ContactResponse.class);
    }

    private List<ContactResponse> convertList(List<ContactEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<ContactResponse>>() {
        }.getType();
        return modelMapper.map(entities, type);
    }

    private Page<ContactResponse> convertPage(Page<ContactEntity> page, Pageable pageable) {
        List<ContactEntity> entities = page.getContent();
        List<ContactResponse> responses = convertList(entities);
        return new PageImpl<>(responses, pageable, page.getTotalElements());
    }
}
