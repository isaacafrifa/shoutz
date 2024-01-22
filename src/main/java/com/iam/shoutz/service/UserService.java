package com.iam.shoutz.service;

import com.iam.shoutz.config.AppConfig;
import com.iam.shoutz.dto.UserRequestDto;
import com.iam.shoutz.dto.UserResponseDto;
import com.iam.shoutz.exception.ResourceAlreadyExists;
import com.iam.shoutz.exception.ResourceNotFound;
import com.iam.shoutz.mapper.UserMapper;
import com.iam.shoutz.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record UserService(UserRepository userRepository, UserMapper userMapper, AppConfig appConfig) {

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::convertToResponseDto)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.username())) {
            throw new ResourceAlreadyExists("User already exists");
        }
        var user = userMapper.convertToEntity(userRequestDto);
        // set default image here
        user.setProfilePictureUrl(appConfig().getDefaultProfileImageUrl());
        var saved = userRepository.save(user);
        return userMapper.convertToResponseDto(saved);
    }

    public void deleteUser(Long id) {
        var foundUser = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        userRepository.delete(foundUser);
    }
}
