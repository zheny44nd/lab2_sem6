package org.example.lab2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedCannotAccessApi() throws Exception {
        mockMvc.perform(get("/api/disciplines"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void studentCanGetButCannotPost() throws Exception {
        mockMvc.perform(get("/api/disciplines"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Math\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "LECTOR")
    void lectorCanManageLessonsButNotDisciplines() throws Exception {
        mockMvc.perform(post("/api/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Physics\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanManageDisciplines() throws Exception {
        mockMvc.perform(post("/api/disciplines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"History\"}"))
                .andExpect(status().isOk());
    }
}
