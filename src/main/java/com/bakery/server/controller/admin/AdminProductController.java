package com.bakery.server.controller.admin;

import com.bakery.server.model.request.NewsAddRequest;
import com.bakery.server.model.request.NewUpdateRequest;
import com.bakery.server.model.request.ProductAddRequest;
import com.bakery.server.model.request.ProductUpdateRequest;
import com.bakery.server.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequestMapping("${admin-base-path}/product")
@RestController
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    private ResponseEntity<?> findAll(String keyword,
                                      @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(productService.findByName(keyword, pageable));
        }
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/category")
    private ResponseEntity<?> findByCategory(Long categoryId,
                                             @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                             @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(productService.findByCategory(categoryId, pageable));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/create-slug")
    private ResponseEntity<?> createSlug(String productName) {
        return ResponseEntity.ok(productService.createSlug(productName));
    }

    @PutMapping
    private ResponseEntity<?> save(@Valid @NotNull @RequestBody ProductAddRequest request) {
        return ResponseEntity.ok(productService.save(request));
    }

    @PostMapping
    private ResponseEntity<?> update(@Valid @NotNull @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.update(request));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }
}
