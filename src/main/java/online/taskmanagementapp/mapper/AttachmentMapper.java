package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.attachment.AttachmentRequestDto;
import online.taskmanagementapp.dto.attachment.AttachmentResponseDto;
import online.taskmanagementapp.models.Attachment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {
    AttachmentResponseDto toResponse(Attachment attachment);

    Attachment toAttachment(AttachmentRequestDto requestDto);
}
