package com.bakery.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Utils {
    public static List<String> getRoles() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return simpleGrantedAuthorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
    }
    public static Boolean validFileType(MultipartFile file) {
        return validFileType(Collections.singletonList(file), Constant.FILE_TYPE.ALL);
    }

    public static Boolean validFileType(MultipartFile file, List<String> typeFile) {
        return validFileType(Collections.singletonList(file), typeFile);
    }

    public static Boolean validFileType(List<MultipartFile> files) {
        return validFileType(files, Constant.FILE_TYPE.ALL);
    }

    public static Boolean validFileType(List<MultipartFile> files, List<String> typeFile) {
        if (CollectionUtils.isEmpty(typeFile)) {
            typeFile = Constant.FILE_TYPE.ALL;
        }
        List<String> contentTypes = files.stream().map(MultipartFile::getContentType).collect(Collectors.toList());
        log.info("ContentType: {}", contentTypes);
        return typeFile.containsAll(contentTypes);
    }
}
