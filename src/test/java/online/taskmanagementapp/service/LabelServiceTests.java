package online.taskmanagementapp.service;

import java.util.List;
import java.util.Optional;
import online.taskmanagementapp.dto.label.LabelRequestDto;
import online.taskmanagementapp.dto.label.LabelResponseDto;
import online.taskmanagementapp.exception.EntityNotFoundException;
import online.taskmanagementapp.mapper.LabelMapper;
import online.taskmanagementapp.models.Label;
import online.taskmanagementapp.repository.LabelRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTests {

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private LabelMapper labelMapper;

    @InjectMocks
    private LabelServiceImpl labelService;

    private Label label;
    private LabelRequestDto requestDto;
    private LabelResponseDto responseDto;

    @BeforeEach
    void setUp() {
        label = new Label();
        label.setId(1L);
        label.setName("Bug");
        label.setColor("#FF0000");

        requestDto = new LabelRequestDto();
        requestDto.setName("Bug");
        requestDto.setColor("#FF0000");

        responseDto = new LabelResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Bug");
        responseDto.setColor("#FF0000");
    }

    @Test
    @DisplayName("create a label")
    void create_ValidRequest_ReturnsLabelResponseDto() {
        when(labelMapper.toLabel(requestDto)).thenReturn(label);
        when(labelRepository.save(label)).thenReturn(label);
        when(labelMapper.toResponse(label)).thenReturn(responseDto);

        LabelResponseDto result = labelService.create(requestDto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Bug");
        assertThat(result.getColor()).isEqualTo("#FF0000");
        verify(labelMapper).toLabel(requestDto);
        verify(labelRepository).save(label);
        verify(labelMapper).toResponse(label);
    }

    @Test
    @DisplayName("find all labels")
    void getLabels_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Label> labelPage = new PageImpl<>(List.of(label), pageable, 1);

        when(labelRepository.findAll(pageable)).thenReturn(labelPage);
        when(labelMapper.toResponse(label)).thenReturn(responseDto);

        Page<LabelResponseDto> result = labelService.getLabels(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Bug");
        assertThat(result.getContent().get(0).getColor()).isEqualTo("#FF0000");
        verify(labelRepository).findAll(pageable);
        verify(labelMapper).toResponse(label);
    }

    @Test
    @DisplayName("update a label")
    void updateLabel_ValidId_ReturnsUpdatedLabelResponseDto() {
        LabelRequestDto updatedRequest = new LabelRequestDto();
        updatedRequest.setName("Feature");
        updatedRequest.setColor("#00FF00");

        LabelResponseDto updatedResponse = new LabelResponseDto();
        updatedResponse.setId(1L);
        updatedResponse.setName("Feature");
        updatedResponse.setColor("#00FF00");

        when(labelRepository.findById(1L)).thenReturn(Optional.of(label));
        when(labelRepository.save(label)).thenReturn(label);
        when(labelMapper.toResponse(label)).thenReturn(updatedResponse);

        LabelResponseDto result = labelService.updateLabel(1L, updatedRequest);

        assertThat(result.getName()).isEqualTo("Feature");
        assertThat(result.getColor()).isEqualTo("#00FF00");
        verify(labelRepository).findById(1L);
        verify(labelMapper).updateLabel(updatedRequest, label);
        verify(labelRepository).save(label);
        verify(labelMapper).toResponse(label);
    }

    @Test
    @DisplayName("update label that don't exist")
    void updateLabel_NonExistingId_ThrowsEntityNotFoundException() {
        when(labelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> labelService.updateLabel(999L, requestDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");

        verify(labelRepository).findById(999L);
        verifyNoMoreInteractions(labelMapper, labelRepository);
    }

    @Test
    @DisplayName("delete label")
    void deleteLabel_ValidId_DeletesLabel() {
        when(labelRepository.existsById(1L)).thenReturn(true);

        labelService.deleteLabel(1L);

        verify(labelRepository).existsById(1L);
        verify(labelRepository).deleteById(1L);
    }

    @Test
    @DisplayName("delete label by fake id")
    void deleteLabel_NonExistingId_ThrowsEntityNotFoundException() {
        when(labelRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> labelService.deleteLabel(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");

        verify(labelRepository).existsById(999L);
        verify(labelRepository, never()).deleteById(any());
    }
}
