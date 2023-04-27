package com.example.hw15.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NewUserDto {

    @Size(min = 2, max = 25, message = "First name must be between 2 and 25 characters")
    private String firstName;

    @Size(min = 2, max = 25, message = "Last name must be between 2 and 25 characters")
    private String lastName;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
            message = "Password must contains 1 Upper and low case Latin character " +
                    "and numeric and consist of 8 characters")
    private String password;

    @Min(value = 5, message = "Age must be between 5 and 150")
    @Max(value = 150, message = "Age must be between 5 and 150")
    private Integer age;
}
