package com.bakery.server.controller.admin;

import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("${admin-base-path}/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

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
