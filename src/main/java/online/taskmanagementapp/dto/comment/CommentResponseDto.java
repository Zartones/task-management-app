package online.taskmanagementapp.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;
import online.taskmanagementapp.models.Task;
import online.taskmanagementapp.models.User;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Task task;
    private User user;
}
