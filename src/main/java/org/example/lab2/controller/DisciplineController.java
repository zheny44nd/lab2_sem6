package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Discipline;
import org.example.lab2.service.DisciplineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    private final DisciplineService service;

    public DisciplineController(DisciplineService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Discipline>> getAllDisciplines() {
        return ApiResponse.success(service.findAll()); // Аналогично весь список
    }

    @GetMapping("/{id}")
    public ApiResponse<Discipline> getDisciplineById(@PathVariable Long id) {
        return ApiResponse.success(service.findById(id));
    }

    @PostMapping
    public ApiResponse<Discipline> createDiscipline(@RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<Discipline> updateDiscipline(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDiscipline(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success(null);
    }
}
