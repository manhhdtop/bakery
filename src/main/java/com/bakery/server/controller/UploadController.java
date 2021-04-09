package com.bakery.server.controller;

import com.bakery.server.model.response.UploadFileResponse;
import com.bakery.server.service.FileService;
import com.bakery.server.utils.AssertUtil;
import com.bakery.server.utils.Constant;
import com.bakery.server.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UploadController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        AssertUtil.isTrue(Utils.validFileType(file, Constant.FILE_TYPE.ALL), "file.file_type.invalid");

        return ResponseEntity.ok(fileService.storeFile(file));
    }

    @PostMapping("/uploads")
    private ResponseEntity<?> uploads(@NotEmpty @RequestParam("files") List<MultipartFile> files) {
        files.forEach(e -> AssertUtil.isTrue(Utils.validFileType(e, Constant.FILE_TYPE.IMAGE), "file.file_type.invalid"));
        List<UploadFileResponse> responses = new ArrayList<>();
        files.forEach(e -> {
            UploadFileResponse response = fileService.storeFile(e);
            responses.add(response);
        });
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/upload-image")
    private ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        AssertUtil.isTrue(Utils.validFileType(file, Constant.FILE_TYPE.IMAGE), "file.file_type.invalid");
        String uploadFolder = "/images";
        return ResponseEntity.ok(fileService.storeFile(file, uploadFolder));
    }

    @PostMapping("/upload-images")
    private ResponseEntity<?> uploadImages(@NotEmpty @RequestParam("files") List<MultipartFile> files) {
        files.forEach(e -> AssertUtil.isTrue(Utils.validFileType(e, Constant.FILE_TYPE.IMAGE), "file.file_type.invalid"));
        List<UploadFileResponse> responses = new ArrayList<>();
        String uploadFolder = "/images";
        files.forEach(e -> {
            UploadFileResponse response = fileService.storeFile(e, uploadFolder);
            responses.add(response);
        });
        return ResponseEntity.ok(responses);
    }
}
