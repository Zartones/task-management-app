package online.taskmanagementapp.mapper;

import online.taskmanagementapp.config.MapperConfig;
import online.taskmanagementapp.dto.label.LabelRequestDto;
import online.taskmanagementapp.dto.label.LabelResponseDto;
import online.taskmanagementapp.models.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    LabelResponseDto toResponse(Label label);

    Label toLabel(LabelRequestDto requestDto);

    void updateLabel(LabelRequestDto dto, @MappingTarget Label label);
}
