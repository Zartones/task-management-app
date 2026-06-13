package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.attachment.AttachmentResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.AttachmentMapper;
import online.taskmanagementapp.models.Attachment;
import online.taskmanagementapp.models.Task;
import online.taskmanagementapp.repository.AttachmentRepository;
import online.taskmanagementapp.repository.TaskRepository;
import online.taskmanagementapp.service.dropbox.DropboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttachmentServiceTests {
    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private DropboxService dropboxService;

    @Mock
    private AttachmentMapper attachmentMapper;

    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    private Task task;
    private Attachment attachment;
    private AttachmentResponseDto responseDto;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");

        attachment = new Attachment();
        attachment.setId(1L);
        attachment.setTask(task);
        attachment.setFilename("test-file.pdf");
        attachment.setDropboxFileId("id:abc123xyz");
        attachment.setUploadDate(LocalDateTime.now());

        responseDto = new AttachmentResponseDto();
        responseDto.setId(1L);
        responseDto.setTask(task);
        responseDto.setFilename("test-file.pdf");
        responseDto.setDropboxFileId("id:abc123xyz");
        responseDto.setUploadDate(LocalDateTime.now());

        multipartFile = new MockMultipartFile(
                "file",
                "test-file.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "test content".getBytes()
        );
    }

    @Test
    @DisplayName("upload and attachment")
    void upload_ValidTaskId_ReturnsAttachmentResponseDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(dropboxService.uploadFile(multipartFile, "tasks/1")).thenReturn("id:abc123xyz");
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);
        when(attachmentMapper.toResponse(attachment)).thenReturn(responseDto);

        AttachmentResponseDto result = attachmentService.upload(1L, multipartFile);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTask()).isEqualTo(task);
        assertThat(result.getFilename()).isEqualTo("test-file.pdf");
        assertThat(result.getDropboxFileId()).isEqualTo("id:abc123xyz");
        verify(taskRepository).findById(1L);
        verify(dropboxService).uploadFile(multipartFile, "tasks/1");
        verify(attachmentRepository).save(any(Attachment.class));
        verify(attachmentMapper).toResponse(attachment);
    }

    @Test
    @DisplayName("upload and attachment for non existing task")
    void upload_NonExistingTaskId_ThrowsEntityNotFoundException() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> attachmentService.upload(999L, multipartFile))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");

        verify(taskRepository).findById(999L);
        verifyNoInteractions(dropboxService, attachmentRepository, attachmentMapper);
    }

    @Test
    @DisplayName("find all attachments for task id")
    void getAttachments_ValidTaskId_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Attachment> attachmentPage = new PageImpl<>(List.of(attachment), pageable, 1);

        when(taskRepository.existsById(1L)).thenReturn(true);
        when(attachmentRepository.findAllByTaskId(1L, pageable)).thenReturn(attachmentPage);
        when(attachmentMapper.toResponse(attachment)).thenReturn(responseDto);

        Page<AttachmentResponseDto> result = attachmentService.getAttachments(1L, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getFilename()).isEqualTo("test-file.pdf");
        assertThat(result.getContent().get(0).getDropboxFileId()).isEqualTo("id:abc123xyz");
        verify(taskRepository).existsById(1L);
        verify(attachmentRepository).findAllByTaskId(1L, pageable);
        verify(attachmentMapper).toResponse(attachment);
    }

    @Test
    @DisplayName("find all attachments for fake task id")
    void getAttachments_NonExistingTaskId_ThrowsEntityNotFoundException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(taskRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> attachmentService.getAttachments(999L, pageable))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");

        verify(taskRepository).existsById(999L);
        verifyNoInteractions(attachmentRepository, attachmentMapper);
    }
}
