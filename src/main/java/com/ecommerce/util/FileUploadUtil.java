package com.ecommerce.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {
    private static FileUploadUtil fileUploadUtil;
    private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/fileUploaded/";

    private FileUploadUtil() {
    }

    public static FileUploadUtil getInstance() {
        if (fileUploadUtil == null)
            fileUploadUtil = new FileUploadUtil();
        return fileUploadUtil;
    }

    public String saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalName = file.getOriginalFilename();
        if (originalName == null)
            originalName = "file";
        String extension = originalName.substring(originalName.lastIndexOf('.'));
        String fileName = UUID.randomUUID() + extension;
        Path path = Paths.get(uploadDir + fileName);
        Files.write(path, bytes);
        return path.toString();
    }
}
