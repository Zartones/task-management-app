package online.taskmanagementapp.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import online.taskmanagementapp.models.Label;
import online.taskmanagementapp.models.Priority;
import online.taskmanagementapp.models.TaskStatus;

@Data
public class TaskRequestDto {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Priority priority;

    @NotNull
    private TaskStatus status;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Long projectId;

    private Long assigneeId;

    private Set<Label> labels = new HashSet<>();
}
