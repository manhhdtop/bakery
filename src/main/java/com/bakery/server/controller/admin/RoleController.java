package com.bakery.server.controller.admin;

import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.service.RoleService;
import com.bakery.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("${admin-base-path}/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Value("${admin.role.code}")
    private String roleAdminCode;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<String> roles = Utils.getRoles();
        if (roles.contains(roleAdminCode)) {
            return ResponseEntity.ok(roleService.findAll());
        }

        return ResponseEntity.ok(roleService.findAllStatusNotHidden());
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody RoleCreateDto roleCreateDto) {
        return ResponseEntity.ok(roleService.save(roleCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody RoleUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(roleService.update(roleUpdateDto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
