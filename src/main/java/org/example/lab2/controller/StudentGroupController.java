package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.SimpleNameDto;
import org.example.lab2.entity.StudentGroup;
import org.example.lab2.service.StudentGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudentGroupController {

    private final StudentGroupService service;

    public StudentGroupController(StudentGroupService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<StudentGroup>> getAllGroups() {
        return ApiResponse.success(service.findAll()); // Список групп весь
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentGroup> getGroupById(@PathVariable Long id) {
        return ApiResponse.success(service.findById(id));
    }

    @PostMapping
    public ApiResponse<StudentGroup> createGroup(@RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentGroup> updateGroup(@PathVariable Long id, @RequestBody @Valid SimpleNameDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGroup(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success(null);
    }
}
