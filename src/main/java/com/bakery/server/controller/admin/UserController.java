package com.bakery.server.controller.admin;

import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("${admin-base-path}/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasPermission('USER', 'ADD')")
    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.save(userCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.update(userUpdateDto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
