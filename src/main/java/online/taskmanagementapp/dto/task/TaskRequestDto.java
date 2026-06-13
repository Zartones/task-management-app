package online.taskmanagementapp.dto.task;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import online.taskmanagementapp.models.Label;
import online.taskmanagementapp.models.Priority;
import online.taskmanagementapp.models.Project;
import online.taskmanagementapp.models.TaskStatus;
import online.taskmanagementapp.models.User;

@Data
public class TaskRequestDto {
    private String name;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private Project project;
    private User assignee;
    private Set<Label> labels = new HashSet<>();
}
