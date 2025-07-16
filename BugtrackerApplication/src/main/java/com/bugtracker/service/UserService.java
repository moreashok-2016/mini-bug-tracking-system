package com.bugtracker.service;

import java.util.List;

import com.bugtracker.dto.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDTO);
	UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDTO);
    void deleteUser(Long id);
}
