package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto create(Long userId, ProjectRequestDto requestDto);

    Page<ProjectResponseDto> getProjects(Long userId, Pageable pageable);

    ProjectResponseDto getProjectById(Long userId, Long id);

    ProjectResponseDto updateProject(Long userId, Long id, ProjectRequestDto projectRequestDto);

    void deleteProject(Long userId, Long id);
}
