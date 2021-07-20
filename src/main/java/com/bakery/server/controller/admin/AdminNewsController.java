package com.bakery.server.controller.admin;

import com.bakery.server.model.request.NewUpdateRequest;
import com.bakery.server.model.request.NewsAddRequest;
import com.bakery.server.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequestMapping("${admin-base-path}/news")
@RestController
public class AdminNewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    private ResponseEntity<?> findAll(String keyword,
                                      @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(newsService.findByName(keyword, pageable));
        }
        return ResponseEntity.ok(newsService.findAll(pageable));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.findById(id));
    }

    @GetMapping("/actives")
    private ResponseEntity<?> actives(@Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(newsService.actives(pageable));
    }

    @GetMapping("/create-slug")
    private ResponseEntity<?> createSlug(String name) {
        return ResponseEntity.ok(newsService.createSlug(name));
    }

    @PutMapping
    private ResponseEntity<?> save(@Valid @NotNull @RequestBody NewsAddRequest request) {
        return ResponseEntity.ok(newsService.save(request));
    }

    @PostMapping
    private ResponseEntity<?> update(@Valid @NotNull @RequestBody NewUpdateRequest request) {
        return ResponseEntity.ok(newsService.update(request));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.delete(id));
    }

    @PostMapping("/{id}/read")
    private ResponseEntity<?> readNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.readNews(id));
    }

    @PostMapping("/{id}/like")
    private ResponseEntity<?> likeNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.likeNews(id));
    }

}
