package org.example.lab2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonAlias;

@Data
public class SimpleNameDto {
    @NotBlank(message = "Name cannot be empty")
    @JsonAlias({"name", "fullName"})
    private String name;
}
