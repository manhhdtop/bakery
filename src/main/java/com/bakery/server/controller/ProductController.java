package com.bakery.server.controller;

import com.bakery.server.model.request.ProductRateRequest;
import com.bakery.server.model.request.ProductRequest;
import com.bakery.server.model.request.RateRequest;
import com.bakery.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    private ResponseEntity<?> findAll(@Valid ProductRequest request) {
        return ResponseEntity.ok(productService.getHomeProduct(request));
    }

    @GetMapping("/{slug}")
    private ResponseEntity<?> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.findBySlug(slug));
    }

    @GetMapping("/rate")
    private ResponseEntity<?> getRates(@Valid ProductRateRequest request) {
        return ResponseEntity.ok(productService.getRates(request));
    }

    @PostMapping("/rate")
    private ResponseEntity<?> rate(@Valid @RequestBody RateRequest request) {
        return ResponseEntity.ok(productService.rate(request));
    }
}
