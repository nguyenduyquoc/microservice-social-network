package com.hdq.identity_service.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class MultipartFileUtil {

    public static boolean isNotEmpty(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public static boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    public static boolean isMultipartFileArrayValid(MultipartFile[] files) {
        return files != null && files.length > 0 && Arrays.stream(files).anyMatch(file -> file != null && !file.isEmpty());
    }

}
