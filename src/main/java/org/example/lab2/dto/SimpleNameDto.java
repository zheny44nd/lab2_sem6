package org.example.lab2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SimpleNameDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}

