package online.taskmanagementapp.service;

import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.attachment.AttachmentResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.AttachmentMapper;
import online.taskmanagementapp.models.Attachment;
import online.taskmanagementapp.models.Task;
import online.taskmanagementapp.repository.AttachmentRepository;
import online.taskmanagementapp.repository.TaskRepository;
import online.taskmanagementapp.service.dropbox.DropboxService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final DropboxService dropboxService;

    @Override
    public AttachmentResponseDto upload(Long taskId, MultipartFile multipartFile) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException("Can't find task with id " + taskId));
        String dropboxFileId = dropboxService.uploadFile(multipartFile, "tasks/" + taskId);
        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setFilename(multipartFile.getOriginalFilename());
        attachment.setDropboxFileId(dropboxFileId);
        return attachmentMapper.toResponse(attachmentRepository.save(attachment));
    }

    @Override
    public Page<AttachmentResponseDto> getAttachments(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Can't find task with id: " + taskId);
        }
        return attachmentRepository.findAllByTaskId(taskId, pageable)
                .map(attachmentMapper::toResponse);
    }
}
