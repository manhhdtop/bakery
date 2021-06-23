package com.bakery.server.controller;

import com.bakery.server.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/catalog")
@RestController
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/provinces")
    private ResponseEntity<?> getListProvince() {
        return ResponseEntity.ok(catalogService.getListProvince());
    }

    @GetMapping("/districts")
    private ResponseEntity<?> getListDistrict(@RequestParam Long provinceId) {
        return ResponseEntity.ok(catalogService.getListDistrict(provinceId));
    }
}
