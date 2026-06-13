package online.taskmanagementapp.service;

import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.ProjectMapper;
import online.taskmanagementapp.models.Project;
import online.taskmanagementapp.repository.ProjectRepository;
import online.taskmanagementapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    @Override
    public ProjectResponseDto create(Long userId, ProjectRequestDto requestDto) {
        Project project = projectMapper.toProject(requestDto);
        project.setUser(userRepository.getReferenceById(userId));
        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @Override
    public Page<ProjectResponseDto> getProjects(Long userId, Pageable pageable) {
        return projectRepository.findAllByUserId(userId, pageable)
                .map(projectMapper::toProjectResponse);
    }

    @Override
    public ProjectResponseDto getProjectById(Long userId, Long id) {
        Project project = projectRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find project with id: " + id
                                + " for user with id: " + userId));
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponseDto updateProject(Long userId, Long id,
                                            ProjectRequestDto projectRequestDto) {
        Project project = projectRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find project with id: " + id
                                + " for user with id: " + userId));
        projectMapper.updateProject(projectRequestDto, project);
        return projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long userId, Long id) {
        projectRepository.deleteByUserIdAndId(userId, id);
    }
}
