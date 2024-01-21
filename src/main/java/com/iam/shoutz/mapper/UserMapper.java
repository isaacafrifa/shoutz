package com.iam.shoutz.mapper;

import com.iam.shoutz.dto.UserRequestDto;
import com.iam.shoutz.dto.UserResponseDto;
import com.iam.shoutz.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User convertToEntity(UserRequestDto userRequestDto) {
       return new User(
                null,
                userRequestDto.username(),
                userRequestDto.firstName(),
                userRequestDto.lastName(),
                null,
                userRequestDto.age(),
                null,
                null
        );
    }

    public UserResponseDto convertToResponseDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePictureUrl(),
                user.getCreatedOn()
        );
    }

}
