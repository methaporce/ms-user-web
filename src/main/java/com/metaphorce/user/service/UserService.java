package com.metaphorce.user.service;

import com.metaphorce.databaseLib.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto createUser(UserDto user);
    UserDto updateUser(Long id, UserDto user);
    void deleteUser(Long id);
}
