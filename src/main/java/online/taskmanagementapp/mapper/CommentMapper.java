package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.comment.CommentRequestDto;
import online.taskmanagementapp.dto.comment.CommentResponseDto;
import online.taskmanagementapp.models.Comment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    CommentResponseDto toResponse(Comment comment);

    Comment toComment(CommentRequestDto requestDto);
}
