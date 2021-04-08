package com.bakery.server.controller;

import com.bakery.server.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UploadController {
    @Autowired
    private FileService fileService;
}
