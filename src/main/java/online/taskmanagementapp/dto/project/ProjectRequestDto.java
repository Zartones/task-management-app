package online.taskmanagementapp.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import online.taskmanagementapp.models.ProjectStatus;

@Data
public class ProjectRequestDto {
    @NotBlank
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private ProjectStatus status;
}
