package com.bakery.server.controller;

import com.bakery.server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {
    @Autowired
    private MailService mailService;

    @GetMapping("/send-mail")
    public ResponseEntity<?> sendMail() {
        mailService.sendMail("hoangmd265@gmail.com", "Test subject", "Test mail");
        return ResponseEntity.ok("Success");
    }
}
