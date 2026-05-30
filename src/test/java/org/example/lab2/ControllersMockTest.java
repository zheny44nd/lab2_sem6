package org.example.lab2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab2.controller.*;
import org.example.lab2.dto.*;
import org.example.lab2.entity.*;
import org.example.lab2.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {
    DisciplineController.class,
    LectorController.class,
    LessonController.class,
    StudentController.class,
    StudentGroupController.class
})
@AutoConfigureMockMvc(addFilters = false) // Disable security for tests
public class ControllersMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private DisciplineService disciplineService;
    @MockBean private LectorService lectorService;
    @MockBean private LessonService lessonService;
    @MockBean private StudentService studentService;
    @MockBean private StudentGroupService studentGroupService;
    @MockBean private AuthService authService; // If security kicks in
    @MockBean private org.example.lab2.security.JwtUtil jwtUtil; // to allow context to load

    @Test
    void testDisciplineController() throws Exception {
        Discipline d = new Discipline(1L, "Math");
        Mockito.when(disciplineService.findAll()).thenReturn(List.of(d));

        mockMvc.perform(get("/api/disciplines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Math"));
    }

    @Test
    void testLectorController() throws Exception {
        Lector l = new Lector(1L, "Ivanov I.I.");
        Mockito.when(lectorService.findAll(0, 10)).thenReturn(List.of(l));

        mockMvc.perform(get("/api/lectors?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fullName").value("Ivanov I.I."));
    }

    @Test
    void testStudentController() throws Exception {
        Student s = new Student(1L, "Petrov P.", new StudentGroup(1L, "G1", null));
        Mockito.when(studentService.findByGroupId(1L)).thenReturn(List.of(s));

        mockMvc.perform(get("/api/students?groupId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fullName").value("Petrov P."));
    }

    @Test
    void testLessonController() throws Exception {
        LessonResponseDto l = new LessonResponseDto();
        l.setId(1L);
        l.setDate(LocalDate.parse("2023-10-10"));
        Mockito.when(lessonService.getLessons(any(), any(), any(), any(), eq(0), eq(10))).thenReturn(List.of(l));

        mockMvc.perform(get("/api/lessons")
                .param("startDate", "2023-10-01")
                .param("endDate", "2023-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}
