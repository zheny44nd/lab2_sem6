package org.example.lab2;

import org.example.lab2.entity.StudentGroup;
import org.example.lab2.repository.StudentGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Disable security for this simple test
@ActiveProfiles("test")
public class StudentGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentGroupRepository groupRepository;

    @BeforeEach
    void setup() {
        groupRepository.deleteAll();
    }

    @Test
    void testGetAllGroups() throws Exception {
        StudentGroup group = new StudentGroup();
        group.setName("Test Group");
        groupRepository.save(group);

        mockMvc.perform(get("/api/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Test Group"));
    }
}

