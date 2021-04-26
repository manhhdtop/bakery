package com.bakery.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

    public static String removeAccent(String s) {
        return removeAccent(s, false);
    }

    public static String removeAccent(String s, boolean isLowerCase) {
        if (isLowerCase) {
            s = s.toLowerCase().replaceAll("đ", "d");
        }

        s = s.replaceAll("đ", "d").trim();

        if (!isLowerCase) {
            s = s.replaceAll("Đ", "D");
        }
        return StringUtils.stripAccents(s);
    }

    public static String createSlug(String name) {
        String slug = removeAccent(name, true);
        slug = slug.replaceAll("\\s+", "-");
        slug += "-" + Utils.formatDate(new Date(), "yyyyMMddhhmmssSSS");
        return slug;
    }

    private static String formatDate(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        if (dateFormat == null) {
            dateFormat = "dd/MM/yyyy";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    private static Date convertDate(String date, String dateFormat) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (dateFormat == null) {
            dateFormat = "dd/MM/yyyy";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(date);
    }
}
