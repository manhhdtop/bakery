package com.bakery.server.controller.admin;

import com.bakery.server.model.request.ActionCreateDto;
import com.bakery.server.model.request.ActionUpdateDto;
import com.bakery.server.service.ActionService;
import com.bakery.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> findAll() {
        List<String> roles = Utils.getRoles();
        if (roles.contains(roleAdminCode)) {
            return ResponseEntity.ok(actionService.findAll());
        }

        return ResponseEntity.ok(actionService.findAllStatusNotHidden());
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
