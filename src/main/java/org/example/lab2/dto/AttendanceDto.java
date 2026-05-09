package org.example.lab2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceDto {
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Presence flag is required")
    private Boolean isPresent;
}

