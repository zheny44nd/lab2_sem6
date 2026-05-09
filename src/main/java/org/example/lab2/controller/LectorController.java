package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Lector;
import org.example.lab2.repository.LectorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectors")
public class LectorController {

    private final LectorRepository repository;

    public LectorController(LectorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<Lector>> getAllLectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Список преподавателей весь, но постранично (из требований)
        return ApiResponse.success(repository.findAll(PageRequest.of(page, size)).getContent());
    }

    @GetMapping("/{id}")
    public ApiResponse<Lector> getLectorById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ApiResponse::success)
                .orElseThrow(() -> new RuntimeException("Lector not found"));
    }

    @PostMapping
    public ApiResponse<Lector> createLector(@RequestBody @Valid SimpleNameDto dto) {
        Lector lector = new Lector();
        lector.setFullName(dto.getName());
        return ApiResponse.success(repository.save(lector));
    }

    @PutMapping("/{id}")
    public ApiResponse<Lector> updateLector(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        Lector lector = repository.findById(id).orElseThrow(() -> new RuntimeException("Lector not found"));
        lector.setFullName(dto.getName());
        return ApiResponse.success(repository.save(lector));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLector(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.success(null);
    }
}

