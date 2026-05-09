package org.example.lab2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentDto {
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotNull(message = "Group ID is required")
    private Long groupId;
}

