package com.practice.file;

public class PathUtils {
    /**
     * CỐ TÌNH DÙNG DẤU GẠCH CHÉO NGƯỢC CỦA WINDOWS '\'
     * Hàm này chạy được trên Windows, nhưng sẽ sai hoàn toàn trên Linux và macOS!
     */
    public String buildFilePath(String folder, String filename) {
        return folder + "\\" + filename; // Lỗi cố tình: dùng cứng dấu '\'
    }
}