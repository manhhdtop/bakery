package com.bakery.server.controller;

import com.bakery.server.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@RestController
public class ProductController {
    @GetMapping
    private ResponseEntity<?> findAll() {
        throw new NotFoundException();
    }
}
