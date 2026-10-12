package com.student.librarymanager.controller;

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
class BookControllerTests {

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
    void bookCrudFlow() throws Exception {
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Dune", "isbn": "9780441172719", "publicationYear": 1965, "authorId": 1}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.available").value(true));
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Solaris", "isbn": "9780156027601", "publicationYear": 1961, "authorId": 2, "available": false}
                        """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        mockMvc.perform(get("/api/books").param("available", "false"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Solaris"));
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"));

        mockMvc.perform(put("/api/books/1").contentType(MediaType.APPLICATION_JSON).content("""
                        {"title": "Dune (Deluxe Edition)", "isbn": "9780593099322", "publicationYear": 2019, "authorId": 1, "available": true}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.publicationYear").value(2019));
        mockMvc.perform(patch("/api/books/1").contentType(MediaType.APPLICATION_JSON).content("""
                        {"available": false}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false))
                .andExpect(jsonPath("$.title").value("Dune (Deluxe Edition)"));

        mockMvc.perform(delete("/api/books/1")).andExpect(status().isNoContent());
        mockMvc.perform(get("/api/books/1")).andExpect(status().isNotFound());
        mockMvc.perform(delete("/api/books/99")).andExpect(status().isNotFound());
        mockMvc.perform(put("/api/books/99").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }
}
