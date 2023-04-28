package com.example.hw15.service.Impl;

import com.example.hw15.dto.NewUserDto;
import com.example.hw15.dto.UserDto;
import com.example.hw15.repository.UserRepository;
import com.example.hw15.repository.entity.User;
import com.example.hw15.service.UserService;
import com.example.hw15.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(NewUserDto newUserDto) {
        User creatingUser = modelMapper.map(newUserDto, User.class);
        String encodedPassword = passwordEncoder.encode(creatingUser.getPassword());
        creatingUser.setPassword(encodedPassword);
        return modelMapper.map(userRepository.save(creatingUser), UserDto.class);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return modelMapper.map(userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email : " + email + " not found")), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User with id : " + userId + " not found");
        }

    }
}
