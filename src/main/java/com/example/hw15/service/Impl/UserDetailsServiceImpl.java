package com.example.hw15.service.Impl;

import com.example.hw15.dto.AuthUserDto;
import com.example.hw15.dto.NewUserDto;
import com.example.hw15.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUserDto user  = modelMapper.map(userService.findUserByEmail(email), AuthUserDto.class);
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

}
