package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.StudentDto;
import org.example.lab2.entity.Student;
import org.example.lab2.entity.StudentGroup;
import org.example.lab2.repository.StudentGroupRepository;
import org.example.lab2.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;
    private final StudentGroupRepository groupRepository;

    public StudentController(StudentRepository repository, StudentGroupRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public ApiResponse<List<Student>> getStudentsByGroup(@RequestParam Long groupId) {
        // Списки студентов - по группе (по заданию)
        return ApiResponse.success(repository.findByGroupId(groupId));
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ApiResponse::success)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody @Valid StudentDto dto) {
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found")); // Валидация зависимости
        Student student = new Student();
        student.setFullName(dto.getFullName());
        student.setGroup(group);
        return ApiResponse.success(repository.save(student));
    }

    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDto dto) {
        Student student = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        student.setFullName(dto.getFullName());
        student.setGroup(group);
        return ApiResponse.success(repository.save(student));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.success(null);
    }
}

