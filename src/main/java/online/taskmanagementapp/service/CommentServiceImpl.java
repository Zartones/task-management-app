package online.taskmanagementapp.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto create(CommentRequestDto requestDto) {
        Task task = taskRepository.findById(requestDto.getTask().getId()).orElseThrow(() ->
                new EntityNotFoundException("Can't find task with id "
                        + requestDto.getTask().getId()));
        User user = userRepository.findById(requestDto.getUser().getId()).orElseThrow(() ->
                new EntityNotFoundException("Can't find user with id "
                        + requestDto.getUser().getId()));
        Comment comment = commentMapper.toComment(requestDto);
        comment.setUser(user);
        comment.setTask(task);
        comment.setTimestamp(LocalDateTime.now());
        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public Page<CommentResponseDto> getComments(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Can't find task with id: " + taskId);
        }
        return commentRepository.findAllByTaskId(taskId, pageable)
                .map(commentMapper::toResponse);
    }
}
