package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.user.UserRequestDto;
import online.taskmanagementapp.dto.user.UserResponseDto;
import online.taskmanagementapp.dto.user.UserRolesRequestDto;
import online.taskmanagementapp.models.User;
import online.taskmanagementapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    @Operation(description = "Update roles")
    public UserResponseDto updateRole(@PathVariable Long id,
                                      @Valid @RequestBody UserRolesRequestDto requestDto) {
        return userService.updateRole(id, requestDto.getRoles());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    @Operation(description = "Get my profile")
    public UserResponseDto getProfile(@AuthenticationPrincipal User user) {
        return userService.get(user.getEmail());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    @Operation(description = "Update my profile")
    public UserResponseDto updateProfile(@AuthenticationPrincipal User user,
                                         @Valid @RequestBody UserRequestDto requestDto) {
        return userService.updateProfile(user.getEmail(), requestDto);
    }
}
