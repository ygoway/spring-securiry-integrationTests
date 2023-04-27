package com.example.hw15.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthUserDto {

    /*@Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "Invalid email format")*/
    /*@Email*/
    private String email;

    private String password;
}
