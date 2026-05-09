package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.AttendanceDto;
import org.example.lab2.dto.LessonDto;
import org.example.lab2.entity.*;
import org.example.lab2.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonRepository lessonRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentGroupRepository groupRepository;
    private final LectorRepository lectorRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public LessonController(LessonRepository lessonRepository, DisciplineRepository disciplineRepository,
                            StudentGroupRepository groupRepository, LectorRepository lectorRepository,
                            StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.lessonRepository = lessonRepository;
        this.disciplineRepository = disciplineRepository;
        this.groupRepository = groupRepository;
        this.lectorRepository = lectorRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping
    public ApiResponse<List<Lesson>> getLessons(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long lectorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Список занятий выдается за период постранично, доп фильтры группа/преподаватель
        Page<Lesson> lessons = lessonRepository.findLessonsWithFilters(
                startDate, endDate, groupId, lectorId, PageRequest.of(page, size));

        // По заданию: список занятий (без данных о посещаемости).
        // @JsonIgnore на attendances в ентити нет, поэтому мы их очистим
        // для этого конкретного вывода, хотя в реальной архитектуре лучше использовать DTO.
        List<Lesson> content = lessons.getContent();
        content.forEach(l -> l.setAttendances(null));

        return ApiResponse.success(content);
    }

    @GetMapping("/{id}")
    public ApiResponse<Lesson> getLessonById(@PathVariable Long id) {
        // По заданию: вместе с данными о посещаемости. Они подтянутся автоматически благодаря связям JPA.
        return lessonRepository.findById(id)
                .map(ApiResponse::success)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @PostMapping
    public ApiResponse<Lesson> createLesson(@RequestBody @Valid LessonDto dto) {
        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new RuntimeException("Lector not found"));

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setGroup(group);
        lesson.setLector(lector);
        lesson.setDate(dto.getDate());
        lesson.setLessonNumber(dto.getLessonNumber());

        return ApiResponse.success(lessonRepository.save(lesson));
    }

    @PutMapping("/{id}")
    public ApiResponse<Lesson> updateLesson(@PathVariable Long id, @RequestBody @Valid LessonDto dto) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new RuntimeException("Discipline not found"));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new RuntimeException("Lector not found"));

        lesson.setDiscipline(discipline);
        lesson.setGroup(group);
        lesson.setLector(lector);
        lesson.setDate(dto.getDate());
        lesson.setLessonNumber(dto.getLessonNumber());

        return ApiResponse.success(lessonRepository.save(lesson));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Long id) {
        lessonRepository.deleteById(id);
        return ApiResponse.success(null);
    }

    // Отметка о посещаемости
    @PostMapping("/{lessonId}/attendance")
    public ApiResponse<Attendance> markAttendance(@PathVariable Long lessonId, @RequestBody @Valid AttendanceDto dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Проверяем, что студент из группы, для которой проводится пара
        if (!student.getGroup().getId().equals(lesson.getGroup().getId())) {
            throw new RuntimeException("Student doesn't belong to this lesson's group");
        }

        Optional<Attendance> existing = attendanceRepository.findByLessonIdAndStudentId(lessonId, dto.getStudentId());

        Attendance attendance;
        if (existing.isPresent()) {
            attendance = existing.get();
            attendance.setIsPresent(dto.getIsPresent());
        } else {
            attendance = new Attendance();
            attendance.setLesson(lesson);
            attendance.setStudent(student);
            attendance.setIsPresent(dto.getIsPresent());
        }

        return ApiResponse.success(attendanceRepository.save(attendance));
    }
}

