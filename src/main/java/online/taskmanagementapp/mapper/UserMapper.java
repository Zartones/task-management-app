package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import online.taskmanagementapp.dto.user.UserRequestDto;
import online.taskmanagementapp.dto.user.UserResponseDto;
import online.taskmanagementapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    @Mapping(target = "username", source = "realUsername")
    UserResponseDto toUserResponse(User user);

    User toUser(UserRegistrationRequestDto requestDto);

    User toUser(UserRequestDto requestDto);
}
