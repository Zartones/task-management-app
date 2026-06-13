package online.taskmanagementapp.service;

import online.taskmanagementapp.dto.label.LabelRequestDto;
import online.taskmanagementapp.dto.label.LabelResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LabelService {
    LabelResponseDto create(LabelRequestDto requestDto);

    Page<LabelResponseDto> getLabels(Pageable pageable);

    LabelResponseDto updateLabel(Long id, LabelRequestDto requestDto);

    void deleteLabel(Long id);

}
