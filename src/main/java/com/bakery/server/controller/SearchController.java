package com.bakery.server.controller;

import com.bakery.server.model.request.SearchRequest;
import com.bakery.server.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/search")
@RestController
@Slf4j
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<?> search(@Valid SearchRequest request) {
        return ResponseEntity.ok(searchService.search(request));
    }
}
