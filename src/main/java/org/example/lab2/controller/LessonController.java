package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.AttendanceDto;
import org.example.lab2.dto.LessonDto;
import org.example.lab2.dto.LessonResponseDto;
import org.example.lab2.entity.Attendance;
import org.example.lab2.service.LessonService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<LessonResponseDto>> getLessons(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long lectorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(service.getLessons(startDate, endDate, groupId, lectorId, page, size));
    }

    @GetMapping("/{id}")
    public ApiResponse<LessonResponseDto> getLessonById(@PathVariable Long id) {
        return ApiResponse.success(service.getLessonById(id));
    }

    @PostMapping
    public ApiResponse<LessonResponseDto> createLesson(@RequestBody @Valid LessonDto dto) {
        return ApiResponse.success(service.createLesson(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<LessonResponseDto> updateLesson(@PathVariable Long id, @RequestBody @Valid LessonDto dto) {
        return ApiResponse.success(service.updateLesson(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Long id) {
        service.deleteLesson(id);
        return ApiResponse.success(null);
    }

    @PostMapping("/{lessonId}/attendance")
    public ApiResponse<Attendance> markAttendance(@PathVariable Long lessonId, @RequestBody @Valid AttendanceDto dto) {
        return ApiResponse.success(service.markAttendance(lessonId, dto));
    }

    @GetMapping("/group/{groupId}")
    public ApiResponse<List<LessonResponseDto>> getAttendanceForGroup(
            @PathVariable Long groupId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.success(service.getAttendanceForGroup(groupId, startDate, endDate));
    }
}