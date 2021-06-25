package com.bakery.server.controller.admin;

import com.bakery.server.model.request.MailTemplateCreateDto;
import com.bakery.server.model.request.MailTemplateUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.service.MailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequestMapping("${admin-base-path}/mail-template")
@RestController
public class MailTemplateController {
    @Autowired
    private MailTemplateService mailTemplateService;

    @GetMapping
    public ResponseEntity<?> findAll(String code,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(mailTemplateService.findAll(code, pageable));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody MailTemplateCreateDto request) {
        return ResponseEntity.ok(mailTemplateService.save(request));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody MailTemplateUpdateDto request) {
        return ResponseEntity.ok(mailTemplateService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        mailTemplateService.delete(id);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
