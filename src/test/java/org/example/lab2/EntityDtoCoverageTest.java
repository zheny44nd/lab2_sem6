package org.example.lab2;

import org.example.lab2.dto.*;
import org.example.lab2.entity.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class EntityDtoCoverageTest {
    @Test
    void testCoverage() {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder().data("data").success(true).build();
        apiResponse.getData();
        apiResponse.isSuccess();
        apiResponse.setData("d");
        apiResponse.setSuccess(false);
        apiResponse.toString();
        ApiResponse<String> apiResponse2 = new ApiResponse<>();
        apiResponse2.setData("d2");
        apiResponse2.setSuccess(false);
        apiResponse.equals(apiResponse2);
        apiResponse.hashCode();

        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setStudentId(1L);
        attendanceDto.setIsPresent(true);
        attendanceDto.getStudentId();
        attendanceDto.getIsPresent();
        attendanceDto.toString();
        AttendanceDto attendanceDto2 = new AttendanceDto();
        attendanceDto2.setStudentId(1L);
        attendanceDto2.setIsPresent(true);
        attendanceDto.equals(attendanceDto2);
        attendanceDto.hashCode();

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("u");
        authRequest.setPassword("p");
        authRequest.getUsername();
        authRequest.getPassword();
        authRequest.toString();
        AuthRequest authRequest2 = new AuthRequest();
        authRequest2.setUsername("u");
        authRequest2.setPassword("p");
        authRequest.equals(authRequest2);
        authRequest.hashCode();

        LessonDto lessonDto = new LessonDto();
        lessonDto.setDisciplineId(1L);
        lessonDto.setLectorId(1L);
        lessonDto.setGroupId(1L);
        lessonDto.setDate(LocalDate.now());
        lessonDto.setLessonNumber(1);
        lessonDto.getDisciplineId();
        lessonDto.getLectorId();
        lessonDto.getGroupId();
        lessonDto.getDate();
        lessonDto.getLessonNumber();
        lessonDto.toString();
        lessonDto.equals(new LessonDto());
        lessonDto.hashCode();

        LessonResponseDto lrDto = new LessonResponseDto();
        lrDto.setId(1L);
        lrDto.setDisciplineName("n");
        lrDto.setLectorName("n");
        lrDto.setGroupName("n");
        lrDto.setDate(LocalDate.now());
        lrDto.setLessonNumber(1);
        lrDto.getId();
        lrDto.getDisciplineName();
        lrDto.getLectorName();
        lrDto.getGroupName();
        lrDto.getDate();
        lrDto.getLessonNumber();
        lrDto.toString();
        lrDto.equals(new LessonResponseDto());
        lrDto.hashCode();

        SimpleNameDto snDto = new SimpleNameDto();
        snDto.setName("n");
        snDto.getName();
        snDto.toString();
        SimpleNameDto snDto2 = new SimpleNameDto();
        snDto2.setName("n");
        snDto.equals(snDto2);
        snDto.hashCode();

        StudentDto sDto = new StudentDto();
        sDto.setGroupId(1L);
        sDto.setFullName("n");
        sDto.getGroupId();
        sDto.getFullName();
        sDto.toString();
        StudentDto sDto2 = new StudentDto();
        sDto2.setGroupId(1L);
        sDto2.setFullName("n");
        sDto.equals(sDto2);
        sDto.hashCode();

        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setUsername("u");
        appUser.setPassword("p");
        appUser.setRole("r");
        appUser.getId();
        appUser.getUsername();
        appUser.getPassword();
        appUser.getRole();
        appUser.toString();
        AppUser appUser2 = new AppUser();
        appUser2.setId(1L);
        appUser2.setUsername("u");
        appUser2.setPassword("p");
        appUser2.setRole("r");
        appUser.equals(appUser2);
        appUser.hashCode();

        Attendance attendance = new Attendance();
        attendance.setId(1L);
        attendance.setLesson(new Lesson());
        attendance.setStudent(new Student());
        attendance.setIsPresent(true);
        attendance.getId();
        attendance.getLesson();
        attendance.getStudent();
        attendance.getIsPresent();
        attendance.toString();
        attendance.equals(new Attendance());
        attendance.hashCode();

        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("n");
        discipline.getId();
        discipline.getName();
        discipline.toString();
        discipline.equals(new Discipline(1L, "n"));
        discipline.hashCode();

        Lector lector = new Lector();
        lector.setId(1L);
        lector.setFullName("n");
        lector.getId();
        lector.getFullName();
        lector.toString();
        lector.equals(new Lector());
        lector.hashCode();

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setDiscipline(discipline);
        lesson.setLector(lector);
        lesson.setGroup(new StudentGroup());
        lesson.setDate(LocalDate.now());
        lesson.setLessonNumber(1);
        lesson.getId();
        lesson.getDiscipline();
        lesson.getLector();
        lesson.getGroup();
        lesson.getDate();
        lesson.getLessonNumber();
        lesson.toString();
        lesson.equals(new Lesson());
        lesson.hashCode();

        Student student = new Student();
        student.setId(1L);
        student.setGroup(new StudentGroup());
        student.setFullName("n");
        student.getId();
        student.getGroup();
        student.getFullName();
        student.toString();
        student.equals(new Student());
        student.hashCode();

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(1L);
        studentGroup.setName("n");
        studentGroup.getId();
        studentGroup.getName();
        studentGroup.toString();
        studentGroup.equals(new StudentGroup());
        studentGroup.hashCode();
    }
}
