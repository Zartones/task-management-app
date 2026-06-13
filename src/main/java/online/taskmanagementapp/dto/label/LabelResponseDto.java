package online.taskmanagementapp.dto.label;

import lombok.Data;
import online.taskmanagementapp.models.User;

@Data
public class LabelResponseDto {
    private Long id;
    private String name;
    private String color;
    private User user;
}
