package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.task.TaskRequestDto;
import online.taskmanagementapp.dto.task.TaskResponseDto;
import online.taskmanagementapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@Tag(name = "Task management", description = "Endpoints for tasks")
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a new task")
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto requestDto) {
        return taskService.create(requestDto);
    }

    @GetMapping
    @Operation(description = "Retrieve project's tasks")
    public Page<TaskResponseDto> getProjectsTasks(@RequestParam Long projectId, Pageable pageable) {
        return taskService.getTasks(projectId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(description = "Retrieve task details")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update task")
    public TaskResponseDto updateTask(@PathVariable Long id,
                                            @Valid @RequestBody TaskRequestDto requestDto) {
        return taskService.updateTask(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete task")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
