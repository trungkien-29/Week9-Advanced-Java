package com.practice.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathUtils {
    // Dùng Logback qua SLF4J thay thế cho System.out.println
    private static final Logger logger = LoggerFactory.getLogger(MathUtils.class);

    public int divide(int a, int b) {
        logger.info("Executing divide operation: {} / {}", a, b);

        if (b == 0) {
            logger.error("Error: Cannot divide by zero! (a = {}, b = {})", a, b);
            throw new ArithmeticException("Cannot divide by zero");
        }

        int result = a / b;
        logger.info("Divide result: {}", result);
        return result;
    }
    public static void main(String[] args){
        MathUtils a = new MathUtils();
        int b = a.divide(10,5);
        System.out.println(b);
    }
}