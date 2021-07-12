package com.bakery.server.controller;

import com.bakery.server.model.request.NewContactDto;
import com.bakery.server.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("contact")
@RestController
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/new-contact")
    public ResponseEntity<?> newContact(@Valid @RequestBody NewContactDto request) {
        return ResponseEntity.ok(contactService.newContact(request));
    }
}
