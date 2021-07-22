package com.bakery.server.controller;

import com.bakery.server.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RequestMapping("news")
@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    private ResponseEntity<?> findAll(@RequestParam(defaultValue = "") String name,
                                      @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(name)) {
            return ResponseEntity.ok(newsService.findByName(name, pageable));
        }
        return ResponseEntity.ok(newsService.findAll(pageable));
    }

    @GetMapping("/home-news")
    private ResponseEntity<?> homeNews() {
        return ResponseEntity.ok(newsService.getHomeNews());
    }

    @GetMapping("/{slug}")
    private ResponseEntity<?> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(newsService.findBySlug(slug));
    }
}
