package online.taskmanagementapp.service;

import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.label.LabelRequestDto;
import online.taskmanagementapp.dto.label.LabelResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.LabelMapper;
import online.taskmanagementapp.models.Label;
import online.taskmanagementapp.repository.LabelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelResponseDto create(LabelRequestDto requestDto) {

        return labelMapper.toResponse(labelRepository.save(labelMapper.toLabel(requestDto)));
    }

    @Override
    public Page<LabelResponseDto> getLabels(Pageable pageable) {
        return labelRepository.findAll(pageable).map(labelMapper::toResponse);
    }

    @Override
    public LabelResponseDto updateLabel(Long id, LabelRequestDto requestDto) {
        Label label = labelRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find label with id: " + id));
        labelMapper.updateLabel(requestDto, label);
        return labelMapper.toResponse(labelRepository.save(label));
    }

    @Override
    public void deleteLabel(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find label with id: " + id);
        }
        labelRepository.deleteById(id);
    }
}
