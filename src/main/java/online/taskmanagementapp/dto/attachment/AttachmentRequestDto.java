package online.taskmanagementapp.dto.attachment;

import java.time.LocalDateTime;
import lombok.Data;
import online.taskmanagementapp.models.Task;

@Data
public class AttachmentRequestDto {
    private Task task;
    private String dropboxFileId;
    private String filename;
    private LocalDateTime uploadDate;
}
