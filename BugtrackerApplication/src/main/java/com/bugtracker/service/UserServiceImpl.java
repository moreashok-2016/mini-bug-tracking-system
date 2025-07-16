package com.bugtracker.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bugtracker.dto.UserDto;
import com.bugtracker.exception.DuplicateResourceException;
import com.bugtracker.exception.ResourceNotFoundException;
import com.bugtracker.model.User;
import com.bugtracker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("User with email '" + userDTO.getEmail() + "' already exists");
        }

        User user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> modelMapper.map(user, UserDto.class))
            .toList();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (!user.getIssues().isEmpty()) {
            throw new IllegalStateException("Cannot delete user who is assigned to issues.");
        }

        userRepository.delete(user);
    }

}
