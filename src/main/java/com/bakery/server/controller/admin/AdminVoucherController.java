package com.bakery.server.controller.admin;

import com.bakery.server.constant.Status;
import com.bakery.server.model.request.VoucherCreateDto;
import com.bakery.server.model.request.VoucherUpdateDto;
import com.bakery.server.model.request.VoucherUpdateStatusDto;
import com.bakery.server.model.response.ApiBaseResponse;
import com.bakery.server.service.VoucherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@PreAuthorize("hasPermission('VOUCHER', 'VIEW')")
@RequestMapping("${admin-base-path}/voucher")
@RestController
public class AdminVoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public ResponseEntity<?> findAll(String keyword,
                                     @Min(1) @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @Min(5) @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (StringUtils.isNotBlank(keyword)) {
            return ResponseEntity.ok(voucherService.findByKeyword(keyword, pageable));
        }
        return ResponseEntity.ok(voucherService.findAll(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<?> findByStatusActive() {
        return ResponseEntity.ok(voucherService.findByStatus(Status.ACTIVE.getStatus()));
    }

    @GetMapping("/find-by-code")
    public ResponseEntity<?> findByCode(@NotBlank @RequestParam("code") String code) {
        return ResponseEntity.ok(voucherService.findByCode(code));
    }

    @PutMapping
    public ResponseEntity<?> save(@Valid @RequestBody VoucherCreateDto request) {
        return ResponseEntity.ok(voucherService.save(request));
    }

    @PostMapping
    public ResponseEntity<?> update(@Valid @RequestBody VoucherUpdateDto request) {
        return ResponseEntity.ok(voucherService.update(request));
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody VoucherUpdateStatusDto request) {
        voucherService.updateStatus(request);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        voucherService.delete(id);
        return ResponseEntity.ok(ApiBaseResponse.success());
    }
}
