package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.attachment.AttachmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentResponseDto upload(Long taskId, MultipartFile multipartFile);

    Page<AttachmentResponseDto> getAttachments(Long taskId, Pageable pageable);
}
