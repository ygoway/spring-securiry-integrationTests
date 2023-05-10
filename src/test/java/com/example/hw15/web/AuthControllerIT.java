/*In progress*/

package com.example.hw15.web;

import com.example.hw15.dto.AuthUserDto;
import com.example.hw15.repository.UserRepository;
import com.example.hw15.repository.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User(1L, "Andr", "Vel", "andrVel@gmail.com", passwordEncoder.encode("Mvc12345"), 20);
        userRepository.save(user);
    }

    @Test
    public void authenticateWithValidCredentials() throws Exception { // 403 Status
        var authUser = new AuthUserDto();
        authUser.setEmail(user.getEmail());
        authUser.setPassword("Mvc12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authUser)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void authenticateWithInvalidCredentialsShouldReturnUnauthorized() throws Exception { // 403 Status
        String invalidEmail = "andrVel@gmail.com";
        String invalidPassword = "12345";

        String requestBody = "{\"email\":\"" + invalidEmail + "\", \"password\":\"" + invalidPassword + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }
}
