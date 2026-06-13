package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.attachment.AttachmentResponseDto;
import online.taskmanagementapp.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@PreAuthorize("isAuthenticated()")
@Tag(name = "Attachment management", description = "Endpoints for attachments")
@RequiredArgsConstructor
@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create a new attachment")
    public AttachmentResponseDto uploadAttachment(@RequestParam Long taskID,
                                                  @RequestParam MultipartFile multipartFile) {
        return attachmentService.upload(taskID, multipartFile);
    }

    @GetMapping
    @Operation(description = "Get attachments for task id")
    public Page<AttachmentResponseDto> getAttachments(@RequestParam Long taskId,
                                                      Pageable pageable) {
        return attachmentService.getAttachments(taskId, pageable);
    }
}
