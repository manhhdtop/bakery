package com.bakery.server.controller;

import com.bakery.server.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("news")
@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    private ResponseEntity<?> findAll() {
        return ResponseEntity.ok(newsService.getHomeNews());
    }

    @GetMapping("/{slug}")
    private ResponseEntity<?> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(newsService.findBySlug(slug));
    }
}
