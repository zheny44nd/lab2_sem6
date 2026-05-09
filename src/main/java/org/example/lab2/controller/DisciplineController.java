package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.Discipline;
import org.example.lab2.repository.DisciplineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplines")
public class DisciplineController {

    private final DisciplineRepository repository;

    public DisciplineController(DisciplineRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<Discipline>> getAllDisciplines() {
        return ApiResponse.success(repository.findAll()); // Аналогично весь список
    }

    @GetMapping("/{id}")
    public ApiResponse<Discipline> getDisciplineById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ApiResponse::success)
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
    }

    @PostMapping
    public ApiResponse<Discipline> createDiscipline(@RequestBody @Valid SimpleNameDto dto) {
        Discipline discipline = new Discipline();
        discipline.setName(dto.getName());
        return ApiResponse.success(repository.save(discipline));
    }

    @PutMapping("/{id}")
    public ApiResponse<Discipline> updateDiscipline(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        Discipline discipline = repository.findById(id).orElseThrow(() -> new RuntimeException("Discipline not found"));
        discipline.setName(dto.getName());
        return ApiResponse.success(repository.save(discipline));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDiscipline(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.success(null);
    }
}

