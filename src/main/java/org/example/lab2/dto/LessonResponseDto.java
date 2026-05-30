package org.example.lab2.dto;

import lombok.Data;

import java.time.LocalDate;

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
}
