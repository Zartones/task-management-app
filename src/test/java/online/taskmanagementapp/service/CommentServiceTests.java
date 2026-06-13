package online.taskmanagementapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import online.taskmanagementapp.dto.comment.CommentRequestDto;
import online.taskmanagementapp.dto.comment.CommentResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.CommentMapper;
import online.taskmanagementapp.models.Comment;
import online.taskmanagementapp.models.Task;
import online.taskmanagementapp.models.User;
import online.taskmanagementapp.repository.CommentRepository;
import online.taskmanagementapp.repository.TaskRepository;
import online.taskmanagementapp.repository.UserRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Task task;
    private User user;
    private Comment comment;
    private CommentRequestDto requestDto;
    private CommentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("Username");

        task = new Task();
        task.setId(1L);
        task.setName("Test Task");

        comment = new Comment();
        comment.setId(1L);
        comment.setTask(task);
        comment.setUser(user);
        comment.setContent("Test comment");
        comment.setTimestamp(LocalDateTime.now());

        requestDto = new CommentRequestDto();
        requestDto.setTask(task);
        requestDto.setUser(user);
        requestDto.setContent("Test comment");

        responseDto = new CommentResponseDto();
        responseDto.setId(1L);
        responseDto.setTask(task);
        responseDto.setUser(user);
        responseDto.setContent("Test comment");
    }

    @Test
    @DisplayName("create a comment")
    void create_ValidRequest_ReturnsCommentResponseDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentMapper.toComment(requestDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toResponse(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.create(requestDto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTask()).isEqualTo(task);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getContent()).isEqualTo("Test comment");
        verify(taskRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(commentRepository).save(comment);
        verify(commentMapper).toResponse(comment);
    }

    @Test
    @DisplayName("get all comments for a task id")
    void getComments_ValidTaskId_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Comment> commentPage = new PageImpl<>(List.of(comment), pageable, 1);

        when(taskRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.findAllByTaskId(1L, pageable)).thenReturn(commentPage);
        when(commentMapper.toResponse(comment)).thenReturn(responseDto);

        Page<CommentResponseDto> result = commentService.getComments(1L, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo("Test comment");
        assertThat(result.getContent().get(0).getTask()).isEqualTo(task);
        verify(taskRepository).existsById(1L);
        verify(commentRepository).findAllByTaskId(1L, pageable);
        verify(commentMapper).toResponse(comment);
    }

    @Test
    @DisplayName("get all comment for fake task id")
    void getComments_NonExistingTaskId_ThrowsEntityNotFoundException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(taskRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> commentService.getComments(999L, pageable))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");

        verify(taskRepository).existsById(999L);
        verifyNoInteractions(commentRepository, commentMapper);
    }
}
