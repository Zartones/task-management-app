package online.taskmanagementapp.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDto {
    @NotBlank
    private String content;

    @NotNull
    private Long taskId;

}
