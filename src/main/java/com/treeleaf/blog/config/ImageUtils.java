package com.treeleaf.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageUtils {
    public String saveThumbnailImage(MultipartFile thumbnailImage) {
        try {
            if (thumbnailImage == null || thumbnailImage.isEmpty()) {
                return null;
            }

            String baseDirectory = new ClassPathResource("static/BlogImages").getFile().getAbsolutePath();
            String fileName = System.currentTimeMillis() + "_" + thumbnailImage.getOriginalFilename();
            Path targetLocation = Paths.get(baseDirectory + File.separator + fileName);
            Files.createDirectories(targetLocation.getParent());
            Files.copy(thumbnailImage.getInputStream(), targetLocation);
            return "/BlogImages/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
