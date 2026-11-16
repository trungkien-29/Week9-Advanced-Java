package com.practice.maven;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    private MathUtils mathUtils;

    @BeforeEach
    void setUp() {
        mathUtils = new MathUtils();
    }

    @Test
    @DisplayName("Kiểm thử phép chia hợp lệ")
    void testDivideSuccess() {
        int result = mathUtils.divide(10, 2);
        assertEquals(5, result, "10 chia 2 phải bằng 5");
    }

    @Test
    @DisplayName("Kiểm thử chia cho 0 phải ném ngoại lệ ArithmeticException")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(10, 0));
    }
}
