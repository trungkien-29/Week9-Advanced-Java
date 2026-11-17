package com.practice.coverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradeServiceTest {

    private final GradeService service = new GradeService();

    @Test
    @DisplayName("Test điểm xuất sắc (>= 8.5)")
    void testExcellentGrade() {
        assertEquals("Xuất sắc", service.classifyGrade(9.0));
    }

    @Test
    @DisplayName("Test điểm giỏi (7.0 - 8.4)")
    void testGoodGrade() {
        assertEquals("Giỏi", service.classifyGrade(7.5));
    }

    @Test
    @DisplayName("Test điểm khá (5.0 - 6.9)")
    void testAverageGrade() {
        assertEquals("Khá", service.classifyGrade(6.0));
    }

    @Test
    @DisplayName("Test điểm yếu (< 5.0)")
    void testPoorGrade() {
        assertEquals("Yếu", service.classifyGrade(3.0));
    }

    @Test
    @DisplayName("Test ngoại lệ khi điểm âm hoặc > 10")
    void testInvalidScore() {
        assertThrows(IllegalArgumentException.class, () -> service.classifyGrade(-1.0));
        assertThrows(IllegalArgumentException.class, () -> service.classifyGrade(11.0));
    }
}