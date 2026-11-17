package com.practice.coverage;

public class GradeService {

    /**
     * Phân loại học lực sinh viên dựa trên điểm số.
     */
    public String classifyGrade(double score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Điểm không hợp lệ, phải từ 0 đến 10");
        }
        if (score >= 8.5) {
            return "Xuất sắc";
        }
        if (score >= 7.0) {
            return "Giỏi";
        }
        if (score >= 5.0) {
            return "Khá";
        }
        return "Yếu";
    }
}