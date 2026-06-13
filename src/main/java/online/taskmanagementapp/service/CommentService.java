package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.comment.CommentRequestDto;
import online.taskmanagementapp.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDto create(CommentRequestDto requestDto);

    Page<CommentResponseDto> getComments(Long taskId, Pageable pageable);
}
