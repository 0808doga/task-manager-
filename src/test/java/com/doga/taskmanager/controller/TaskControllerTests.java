package com.doga.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Runs the same flow as the Postman collection (Task 6): create, list, get, update, patch, delete, 404.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloEndpoints() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring Boot!"));
        mockMvc.perform(get("/api/hello/John"))
                .andExpect(content().string("Hello, John!"));
        mockMvc.perform(post("/api/hello").contentType(MediaType.TEXT_PLAIN).content("Test message"))
                .andExpect(content().string("You sent: Test message"));
    }

    @Test
    void taskCrudFlow() throws Exception {
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Complete Lab 1", "description": "Finish all tasks for laboratory 1", "completed": false}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.completed").value(false));
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Install Postman", "description": "Download and install", "completed": true}
                        """))
                .andExpect(status().isCreated());
        // "completed" omitted -> defaults to false
        mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Read lecture 2"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.completed").value(false));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
        mockMvc.perform(get("/api/tasks").param("completed", "true"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Install Postman"));
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Complete Lab 1"));

        mockMvc.perform(put("/api/tasks/1").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Complete Lab 1", "description": "Finish all tasks for laboratory 1", "completed": true}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.completed").value(true));
        mockMvc.perform(patch("/api/tasks/1").contentType(MediaType.APPLICATION_JSON).content("""
                        {"description": "Done"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Done"))
                .andExpect(jsonPath("$.completed").value(true));

        mockMvc.perform(delete("/api/tasks/1")).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/tasks/1")).andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/tasks/99")).andExpect(status().isNotFound());
        mockMvc.perform(put("/api/tasks/99").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void projectAndCategoryCrud() throws Exception {
        mockMvc.perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON).content("""
                        {"name": "University", "description": "Semester assignments"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
        mockMvc.perform(get("/api/projects/1")).andExpect(jsonPath("$.name").value("University"));
        mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content("""
                        {"name": "Urgent", "color": "#FF0000"}
                        """))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/api/categories/1")).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/categories/1")).andExpect(status().isNotFound());
    }
}
