package com.bakery.server.controller.admin;

import com.bakery.server.constant.Status;
import com.bakery.server.model.request.RoleCreateDto;
import com.bakery.server.model.request.RoleUpdateDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.service.RoleService;
import com.bakery.server.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("${admin-base-path}/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

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
                return ResponseEntity.ok(roleService.findByName(keyword, pageable));
            }
            return ResponseEntity.ok(roleService.findAll(pageable));
        }

        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(roleService.findByNameNotHidden(keyword, pageable));
        }
        return ResponseEntity.ok(roleService.findAllStatusNotHidden(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<?> findByStatusActive() {
        return ResponseEntity.ok(roleService.findByStatus(Status.ACTIVE.getStatus()));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody RoleCreateDto roleCreateDto) {
        return ResponseEntity.ok(roleService.save(roleCreateDto));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody RoleUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(roleService.update(roleUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
