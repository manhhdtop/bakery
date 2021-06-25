package com.bakery.server.service.impl;

import com.bakery.server.entity.MailTemplateEntity;
import com.bakery.server.model.request.MailTemplateCreateDto;
import com.bakery.server.model.request.MailTemplateUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.MailTemplateResponse;
import com.bakery.server.repository.MailTemplateRepository;
import com.bakery.server.service.MailTemplateService;
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
import java.util.Collections;
import java.util.List;

@Service
public class MailTemplateServiceImpl implements MailTemplateService {
    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiBaseResponse findAll(String code, Pageable pageable) {
        if (StringUtils.isNotBlank(code)) {
            MailTemplateEntity entity = mailTemplateRepository.findByCode(code);
            if (entity == null) {
                return ApiBaseResponse.success(new PageImpl<>(new ArrayList<>(), pageable, 0));
            }
            List<MailTemplateEntity> entities = Collections.singletonList(entity);
            return ApiBaseResponse.success(convertPage(new PageImpl<>(entities, pageable, 1)));
        }
        return ApiBaseResponse.success(convertPage(mailTemplateRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse save(MailTemplateCreateDto request) {
        request.validate();
        MailTemplateEntity byCode = mailTemplateRepository.findByCode(request.getCode());
        AssertUtil.isNull(byCode, "mail_template.code.exist");
        MailTemplateEntity entity = modelMapper.map(request, MailTemplateEntity.class);
        entity = mailTemplateRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse update(MailTemplateUpdateDto request) {
        request.validate();
        MailTemplateEntity entity = mailTemplateRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "mail_template.not_exist");
        modelMapper.map(request, entity);
        entity = mailTemplateRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public void delete(Long id) {
        MailTemplateEntity entity = mailTemplateRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(1);
            mailTemplateRepository.save(entity);
        }
    }

    private MailTemplateResponse convert(MailTemplateEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, MailTemplateResponse.class);
    }

    private List<MailTemplateResponse> convertList(List<MailTemplateEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<MailTemplateResponse>>() {
        }.getType();
        return modelMapper.map(entities, type);
    }

    private Page<MailTemplateResponse> convertPage(Page<MailTemplateEntity> page) {
        List<MailTemplateEntity> entities = page.getContent();
        return new PageImpl<>(convertList(entities), page.getPageable(), page.getTotalElements());
    }
}
