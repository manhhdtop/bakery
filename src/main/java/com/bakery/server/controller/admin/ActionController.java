package com.bakery.server.controller.admin;

import com.bakery.server.constant.Status;
import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.service.ActionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@PreAuthorize("hasPermission('ACTION', 'VIEW')")
@RequestMapping("${admin-base-path}/action")
@RestController
public class ActionController {
    @Autowired
    private ActionService actionService;

    @GetMapping
    public ResponseEntity<?> findAll(String keyword,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(actionService.findByKeyword(keyword, pageable));
        }
        return ResponseEntity.ok(actionService.findAll(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<?> findByStatusActive() {
        return ResponseEntity.ok(actionService.findByStatus(Status.ACTIVE.getStatus()));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody ActionCreateDto roleCreateDto) {
        return ResponseEntity.ok(actionService.save(roleCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody ActionUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(actionService.update(roleUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        actionService.delete(id);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
