package com.example.hw15.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthUserDto {

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "Invalid email format")
    private String email;

    private String password;
}
