package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.StudentDto;
import org.example.lab2.entity.Student;
import org.example.lab2.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Student>> getStudentsByGroup(@RequestParam Long groupId) {
        return ApiResponse.success(service.findByGroupId(groupId));
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        return ApiResponse.success(service.findById(id));
    }

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody @Valid StudentDto dto) {
        return ApiResponse.success(service.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDto dto) {
        return ApiResponse.success(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success(null);
    }
}
