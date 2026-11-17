package com.practice.file;

import java.nio.file.Path;

public class PathUtils {
    /**
     * Dùng Path.of để Java tự động nhận diện hệ điều hành:
     * - Chạy trên Windows: tự điền dấu '\'
     * - Chạy trên Linux/Mac: tự điền dấu '/'
     */
    public String buildFilePath(String folder, String filename) {
        return Path.of(folder, filename).toString();
    }
}