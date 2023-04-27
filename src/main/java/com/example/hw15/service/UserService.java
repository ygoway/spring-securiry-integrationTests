package com.example.hw15.service;

import com.example.hw15.dto.NewUserDto;
import com.example.hw15.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserDto newUserDto);

    UserDto findUserByEmail(String email);

    List<UserDto> getAllUsers();

    void deleteUserById(Long userId);
}
