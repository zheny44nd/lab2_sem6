package org.example.lab2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LessonDto {
    @NotNull(message = "Discipline ID is required")
    private Long disciplineId;

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "Lector ID is required")
    private Long lectorId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Lesson number is required")
    private Integer lessonNumber;
}

