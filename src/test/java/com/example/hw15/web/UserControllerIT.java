package com.example.hw15.web;

import com.example.hw15.repository.UserRepository;
import com.example.hw15.repository.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;


    @BeforeEach
    void setUp() throws Exception {
        user = new User(1L, "Andr", "Vel", "andrVel@gmail.com", "Mvc12345", 20);
        userRepository.save(user);
    }

    @SneakyThrows
    @Test
    public void createValidUser() {
        String stringUser = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringUser))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserWithInvalidEmailAndProofCorrectHandlerResponse() throws Exception {
        var invalidUser = new User(3L, "Andr", "Vel", "123123", "Mvc12345", 20);
        String badEmailRequest = "Invalid email format";
        var emailMock = mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseBody = emailMock.getResponse().getContentAsString();
        Map<String, String> actualResponse = objectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {});

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("email", "Invalid email format");

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        String stringUser = objectMapper.writeValueAsString(user);
        mockMvc.perform(get("/api/users/user/andrVel@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("Andr"))
                .andExpect(jsonPath("lastName").value("Vel"))
                .andExpect(jsonPath("email").value("andrVel@gmail.com"))
                .andExpect(jsonPath("password").value("Mvc12345"));
    }

    @Test
    @WithMockUser
    public void deleteByUserIdTest() throws Exception {
        mockMvc.perform(delete("/api/users/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


