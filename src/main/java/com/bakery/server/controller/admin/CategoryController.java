package com.bakery.server.controller.admin;

import com.bakery.server.model.request.AddCategoryRequest;
import com.bakery.server.model.request.UpdateCategoryRequest;
import com.bakery.server.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequestMapping("${admin-base-path}/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    private ResponseEntity<?> findAll(String name,
                                      @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(name)) {
            return ResponseEntity.ok(categoryService.findByName(name, pageable));
        }
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("/parents")
    private ResponseEntity<?> findListParent() {
        return ResponseEntity.ok(categoryService.findListParent());
    }

    @GetMapping("/active")
    private ResponseEntity<?> findListActive() {
        return ResponseEntity.ok(categoryService.findListActive());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping
    private ResponseEntity<?> save(@Valid @NotNull @RequestBody AddCategoryRequest request) {
        return ResponseEntity.ok(categoryService.save(request));
    }

    @PostMapping
    private ResponseEntity<?> update(@Valid @NotNull @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(request));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}
