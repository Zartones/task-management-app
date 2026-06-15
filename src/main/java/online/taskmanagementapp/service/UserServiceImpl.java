package online.taskmanagementapp.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import online.taskmanagementapp.dto.user.UserRequestDto;
import online.taskmanagementapp.dto.user.UserResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.exception.RegistrationException;
import online.taskmanagementapp.mapper.UserMapper;
import online.taskmanagementapp.models.Role;
import online.taskmanagementapp.models.User;
import online.taskmanagementapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with email: "
                    + requestDto.getEmail() + " already exists");
        }
        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto updateRole(Long id, Set<Role> roles) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find user with id:" + id));

        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto get(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto updateProfile(String email, UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        user.setUsername(userRequestDto.getUsername());
        user.setLastName(userRequestDto.getLastName());
        user.setFirstName(userRequestDto.getFirstName());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
