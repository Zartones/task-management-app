package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import online.taskmanagementapp.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    ProjectResponseDto toProjectResponse(Project project);

    Project toProject(ProjectRequestDto requestDto);

    void updateProject(ProjectRequestDto dto, @MappingTarget Project project);
}
