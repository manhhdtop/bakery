package com.bakery.server.controller;

import com.bakery.server.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RequestMapping("voucher")
@RestController
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/generate-code")
    public ResponseEntity<?> generateCode() {
        return ResponseEntity.ok(voucherService.generateCode());
    }

    @GetMapping("/check-code")
    public ResponseEntity<?> checkCode(@NotBlank @RequestParam("code") String code) {
        return ResponseEntity.ok(voucherService.checkCode(code));
    }
}
