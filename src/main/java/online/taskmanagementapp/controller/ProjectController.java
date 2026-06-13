package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import online.taskmanagementapp.models.User;
import online.taskmanagementapp.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@Tag(name = "Project management", description = "Endpoints for projects")
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a new project")
    public ProjectResponseDto createProject(@AuthenticationPrincipal User user,
                                            @Valid @RequestBody ProjectRequestDto requestDto) {
        return projectService.create(user.getId(), requestDto);
    }

    @GetMapping
    @Operation(description = "Retrieve user's projects")
    public Page<ProjectResponseDto> getUserProjects(@AuthenticationPrincipal User user,
                                                    Pageable pageable) {
        return projectService.getProjects(user.getId(), pageable);
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieve project details")
    public ProjectResponseDto getProjectById(@AuthenticationPrincipal User user,
                                             @PathVariable Long id) {
        return projectService.getProjectById(user.getId(), id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update project")
    public ProjectResponseDto updateProject(@AuthenticationPrincipal User user,
                                            @PathVariable Long id,
                                            @Valid @RequestBody
                                                ProjectRequestDto projectRequestDto) {
        return projectService.updateProject(user.getId(), id, projectRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete project")
    public void deleteProject(@AuthenticationPrincipal User user,
                              @PathVariable Long id) {
        projectService.deleteProject(user.getId(), id);
    }
}
