package com.arakviel.localblog.domain.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

final class ImageUtil {

    private ImageUtil() {
    }

    public static String copyToImages(String avatarPath) throws IOException {
        Path source = Paths.get(avatarPath);
        String newFileName = "%s.%s".formatted(UUID.randomUUID(),
                getFileExtension(source.getFileName().toString()));
        Path target = Path.of("Images", "Avatars", newFileName);
        Files.copy(source, target);

        return target.toString();
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
