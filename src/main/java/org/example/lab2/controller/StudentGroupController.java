package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.StudentGroup;
import org.example.lab2.repository.StudentGroupRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudentGroupController {

    private final StudentGroupRepository repository;

    public StudentGroupController(StudentGroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<StudentGroup>> getAllGroups() {
        return ApiResponse.success(repository.findAll()); // Список групп весь
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentGroup> getGroupById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ApiResponse::success)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @PostMapping
    public ApiResponse<StudentGroup> createGroup(@RequestBody @Valid SimpleNameDto dto) {
        StudentGroup group = new StudentGroup();
        group.setName(dto.getName());
        return ApiResponse.success(repository.save(group));
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentGroup> updateGroup(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        StudentGroup group = repository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));
        group.setName(dto.getName());
        return ApiResponse.success(repository.save(group));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGroup(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.success(null);
    }
}

