package online.taskmanagementapp.service;

import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.task.TaskRequestDto;
import online.taskmanagementapp.dto.task.TaskResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.TaskMapper;
import online.taskmanagementapp.models.Task;
import online.taskmanagementapp.repository.ProjectRepository;
import online.taskmanagementapp.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public TaskResponseDto create(TaskRequestDto requestDto) {
        Task task = taskMapper.toTask(requestDto);
        task.setProject(projectRepository.getReferenceById(requestDto.getProject().getId()));
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponseDto> getTasks(Long projectId, Pageable pageable) {
        return taskRepository.findAllByProjectId(projectId, pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find task with id: " + id));
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto requestDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find task with id: " + id));
        taskMapper.taskUpdate(requestDto, task);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
