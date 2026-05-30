package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Lector;
import org.example.lab2.service.LectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectors")
public class LectorController {

    private final LectorService service;

    public LectorController(LectorService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Lector>> getAllLectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(service.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ApiResponse<Lector> getLectorById(@PathVariable Long id) {
        return ApiResponse.success(service.findById(id));
    }

    @PostMapping
    public ApiResponse<Lector> createLector(@RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<Lector> updateLector(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLector(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success(null);
    }
}
