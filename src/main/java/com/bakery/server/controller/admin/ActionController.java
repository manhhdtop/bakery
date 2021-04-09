package com.bakery.server.controller.admin;

import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.service.ActionService;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@PreAuthorize("hasPermission('ACTION', 'VIEW')")
@RequestMapping("${admin-base-path}/action")
@RestController
public class ActionController {
    @Autowired
    private ActionService actionService;

    @Value("${admin.role.code}")
    private String roleAdminCode;

    @GetMapping
    public ResponseEntity<?> findAll(String keyword,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<String> roles = Utils.getRoles();
        if (roles.contains(roleAdminCode)) {
            if (StringUtils.isNotBlank(keyword)) {
                return ResponseEntity.ok(actionService.findByKeyword(keyword, pageable));
            }
            return ResponseEntity.ok(actionService.findAll(pageable));
        }

        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(actionService.findByKeywordNotHidden(keyword, pageable));
        }
        return ResponseEntity.ok(actionService.findByStatusNotHidden(pageable));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody ActionCreateDto roleCreateDto) {
        return ResponseEntity.ok(actionService.save(roleCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody ActionUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(actionService.update(roleUpdateDto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Long id) {
        actionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
