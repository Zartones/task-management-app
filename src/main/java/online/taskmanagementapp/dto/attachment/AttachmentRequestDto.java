package online.taskmanagementapp.dto.attachment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttachmentRequestDto {
    @NotNull
    private Long taskId;
}
