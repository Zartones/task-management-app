package online.taskmanagementapp.service;

import java.util.Set;
import online.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import online.taskmanagementapp.dto.user.UserRequestDto;
import online.taskmanagementapp.dto.user.UserResponseDto;
import online.taskmanagementapp.exception.RegistrationException;
import online.taskmanagementapp.models.Role;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto updateRole(Long id, Set<Role> roles);

    UserResponseDto get(String email);

    UserResponseDto updateProfile(String email, UserRequestDto requestDto);
}
