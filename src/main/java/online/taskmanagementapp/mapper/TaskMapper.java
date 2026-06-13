package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.task.TaskRequestDto;
import online.taskmanagementapp.dto.task.TaskResponseDto;
import online.taskmanagementapp.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    TaskResponseDto toResponse(Task task);

    Task toTask(TaskRequestDto requestDto);

    void taskUpdate(TaskRequestDto requestDto, @MappingTarget Task task);
}
