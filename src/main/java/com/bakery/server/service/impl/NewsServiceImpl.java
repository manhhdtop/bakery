package com.bakery.server.service.impl;

import com.bakery.server.entity.NewsEntity;
import com.bakery.server.model.request.NewUpdateRequest;
import com.bakery.server.model.request.NewsAddRequest;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.model.response.NewsResponse;
import com.bakery.server.repository.NewsRepository;
import com.bakery.server.service.NewsService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public ApiBaseResponse findAll(Pageable pageable) {
        return ApiBaseResponse.success(convertPage(newsRepository.findAll(pageable)));
    }

    @Override
    public ApiBaseResponse findByName(String name, Pageable pageable) {
        return ApiBaseResponse.success(convertPage(newsRepository.findByNameContaining(name, pageable)));
    }

    @Override
    public ApiBaseResponse findById(Long id) {
        NewsEntity entity = newsRepository.findById(id).orElse(null);
        AssertUtil.notNull(entity, "news.not_found");
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse save(NewsAddRequest request) {
        request.validData();
        NewsEntity entity = modelMapper.map(request, NewsEntity.class);
        entity = newsRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse update(NewUpdateRequest request) {
        request.validData();
        NewsEntity entity = newsRepository.findById(request.getId()).orElse(null);
        AssertUtil.notNull(entity, "news.not_found");
        NewsEntity entityBySlug = newsRepository.findBySlugLike(request.getSlug());
        if (entityBySlug != null) {
            AssertUtil.isTrue(entityBySlug.getId().equals(entity.getId()), "news.slug_exist");
        }
        modelMapper.map(request, entity);
        entity = newsRepository.save(entity);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse delete(Long id) {
        NewsEntity entity = newsRepository.findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(1);
            newsRepository.save(entity);
        }
        return ApiBaseResponse.success();
    }

    @Override
    public ApiBaseResponse createSlug(String name) {
        String slug = Utils.createSlug(name);
        NewsEntity entityBySlug = newsRepository.findBySlugLike(slug);
        if (entityBySlug != null) {
            String lastSlug = entityBySlug.getSlug();
            slug = Utils.createNewSlug(slug, lastSlug);
        }
        return ApiBaseResponse.success(slug);
    }

    @Override
    public ApiBaseResponse findBySlug(String slug) {
        NewsEntity entity = newsRepository.findBySlug(slug);
        AssertUtil.notNull(entity, "news.not_found");
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse readNews(Long id) {
        NewsEntity entity = newsRepository.findById(id).orElse(null);
        AssertUtil.notNull(entity, "news.not_found");
        newsRepository.readNews(id);
        entity = newsRepository.findById(id).orElse(null);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse likeNews(Long id) {
        NewsEntity entity = newsRepository.findById(id).orElse(null);
        AssertUtil.notNull(entity, "news.not_found");
        newsRepository.likeNews(id);
        entity = newsRepository.findById(id).orElse(null);
        return ApiBaseResponse.success(convert(entity));
    }

    @Override
    public ApiBaseResponse getHomeNews() {
        return ApiBaseResponse.success(convertPage(newsRepository.getActiveNews(Pageable.unpaged())));
    }

    private NewsResponse convert(NewsEntity entity) {
        if (entity == null) {
            return null;
        }
        return modelMapper.map(entity, NewsResponse.class);
    }

    private List<NewsResponse> convertList(List<NewsEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<NewsResponse>>() {
        }.getType();
        return modelMapper.map(entities, type);
    }

    private Page<NewsResponse> convertPage(Page<NewsEntity> page) {
        List<NewsEntity> entities = page.getContent();
        return new PageImpl<>(convertList(entities), page.getPageable(), page.getTotalElements());
    }
}
