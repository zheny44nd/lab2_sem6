package org.example.lab2.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LessonResponseDto {
    private Long id;

    private Long disciplineId;
    private String disciplineName;

    private Long groupId;
    private String groupName;

    private Long lectorId;
    private String lectorName;

    private LocalDate date;
    private Integer lessonNumber;
    
    // Add attendance information for the lesson
    private List<AttendanceInfo> attendances;
    
    @Data
    public static class AttendanceInfo {
        private Long studentId;
        private String studentName;
        private Boolean present; // Changed from isPresent to present for consistency
    }
}