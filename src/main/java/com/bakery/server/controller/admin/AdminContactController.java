package com.bakery.server.controller.admin;

import com.bakery.server.model.request.ContactUpdateStatusDto;
import com.bakery.server.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequestMapping("${admin-base-path}/contact")
@RestController
public class AdminContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam(value = "keyword", required = false) String keyword,
                                    @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(contactService.findAll(keyword, pageable));
    }

    @PostMapping("update-status")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody ContactUpdateStatusDto request) {
        return ResponseEntity.ok(contactService.updateStatus(request));
    }
}
