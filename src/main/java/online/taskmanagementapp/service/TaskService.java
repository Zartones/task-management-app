package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.task.TaskRequestDto;
import online.taskmanagementapp.dto.task.TaskResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto create(TaskRequestDto requestDto);

    Page<TaskResponseDto> getTasks(Long projectId, Pageable pageable);

    TaskResponseDto getTaskById(Long id);

    TaskResponseDto updateTask(Long id, TaskRequestDto requestDto);

    void deleteTask(Long id);
}
