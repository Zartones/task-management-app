package online.taskmanagementapp.dto.project;

import java.time.LocalDate;
import lombok.Data;
import online.taskmanagementapp.models.ProjectStatus;

@Data
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
}
