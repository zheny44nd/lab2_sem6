package org.example.lab2.service;

import org.example.lab2.dto.AttendanceDto;
import org.example.lab2.dto.LessonDto;
import org.example.lab2.dto.LessonResponseDto;
import org.example.lab2.entity.*;
import org.example.lab2.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LessonService {
    private final LessonRepository lessonRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentGroupRepository groupRepository;
    private final LectorRepository lectorRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public LessonService(LessonRepository lessonRepository, DisciplineRepository disciplineRepository,
                         StudentGroupRepository groupRepository, LectorRepository lectorRepository,
                         StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.lessonRepository = lessonRepository;
        this.disciplineRepository = disciplineRepository;
        this.groupRepository = groupRepository;
        this.lectorRepository = lectorRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional(readOnly = true)
    public List<LessonResponseDto> getLessons(LocalDate startDate, LocalDate endDate, Long groupId, Long lectorId, int page, int size) {
        return lessonRepository.findLessonsWithFilters(
                startDate, endDate, groupId, lectorId, PageRequest.of(page, size))
                .getContent().stream()
                .map(this::mapToDto).toList();
    }

    public LessonResponseDto getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lesson not found"));
        return mapToDto(lesson);
    }

    public LessonResponseDto createLesson(LessonDto dto) {
        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Discipline not found"));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Group not found"));
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lector not found"));

        Lesson lesson = new Lesson();
        lesson.setDiscipline(discipline);
        lesson.setGroup(group);
        lesson.setLector(lector);
        lesson.setDate(dto.getDate());
        lesson.setLessonNumber(dto.getLessonNumber());

        return mapToDto(lessonRepository.save(lesson));
    }

    public LessonResponseDto updateLesson(Long id, LessonDto dto) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lesson not found"));
        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Discipline not found"));
        StudentGroup group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Group not found"));
        Lector lector = lectorRepository.findById(dto.getLectorId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lector not found"));

        lesson.setDiscipline(discipline);
        lesson.setGroup(group);
        lesson.setLector(lector);
        lesson.setDate(dto.getDate());
        lesson.setLessonNumber(dto.getLessonNumber());

        return mapToDto(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public Attendance markAttendance(Long lessonId, AttendanceDto dto) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Lesson not found"));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new org.example.lab2.exception.ResourceNotFoundException("Student not found"));

        if (!student.getGroup().getId().equals(lesson.getGroup().getId())) {
            throw new org.example.lab2.exception.BadRequestException("Student doesn't belong to this lesson's group");
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

        return attendanceRepository.save(attendance);
    }

    private LessonResponseDto mapToDto(Lesson l) {
        LessonResponseDto dto = new LessonResponseDto();
        dto.setId(l.getId());
        dto.setDisciplineId(l.getDiscipline().getId());
        dto.setDisciplineName(l.getDiscipline().getName());
        dto.setGroupId(l.getGroup().getId());
        dto.setGroupName(l.getGroup().getName());
        dto.setLectorId(l.getLector().getId());
        dto.setLectorName(l.getLector().getFullName());
        dto.setDate(l.getDate());
        dto.setLessonNumber(l.getLessonNumber());
        return dto;
    }
}
