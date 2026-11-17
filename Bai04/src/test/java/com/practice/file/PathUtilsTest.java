package com.practice.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathUtilsTest {

    @Test
    @DisplayName("Kiểm thử đường dẫn file tương thích đa nền tảng")
    void testBuildFilePath() {
        PathUtils pathUtils = new PathUtils();
        String result = pathUtils.buildFilePath("data", "users.txt");

        // Kỳ vọng: Đường dẫn phải tự động khớp với dấu phân cách của hệ điều hành đang chạy
        String expected = "data" + File.separator + "users.txt";

        // Trên Windows: expected là "datausers.txt" -> KHỚP VỚI RESULT -> PASS
        // Trên Ubuntu & macOS: expected là "data/users.txt" -> LỆCH VỚI RESULT -> FAIL NGAY!
        assertEquals(expected, result);
    }
}