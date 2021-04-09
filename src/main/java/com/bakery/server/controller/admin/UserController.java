package com.bakery.server.controller.admin;

import com.bakery.server.model.request.UserCreateDto;
import com.bakery.server.model.request.UserUpdateDto;
import com.bakery.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequestMapping("${admin-base-path}/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll(String keyword,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(userService.findByName(keyword, pageable));
        }
        return ResponseEntity.ok(userService.findAll(pageable));
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
