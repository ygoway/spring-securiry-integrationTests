package com.example.hw15.web;

import com.example.hw15.repository.BookRepository;
import com.example.hw15.repository.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void cleanDB() {
        bookRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void createBookTest() throws Exception {
        var testBook = new Book(1L, "CleanCode", "RobertMartin", 400);
        mockMvc.perform(post("/api/books/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(testBook.getName()))
                .andExpect(jsonPath("author").value(testBook.getAuthor()));
    }
}
