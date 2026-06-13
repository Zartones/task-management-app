package online.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.taskmanagementapp.dto.label.LabelRequestDto;
import online.taskmanagementapp.dto.label.LabelResponseDto;
import online.taskmanagementapp.service.LabelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@Tag(name = "Label management", description = "Endpoints for labels")
@RequiredArgsConstructor
@RestController
@RequestMapping("/labels")
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    @Operation(description = "Create a label")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelResponseDto createLabel(@Valid @RequestBody LabelRequestDto requestDto) {
        return labelService.create(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a label")
    public LabelResponseDto updateLabel(@PathVariable Long id,
                                        @Valid @RequestBody LabelRequestDto requestDto) {
        return labelService.updateLabel(id, requestDto);
    }

    @GetMapping
    @Operation(description = "Get all labels")
    public Page<LabelResponseDto> getLabels(Pageable pageable) {
        return labelService.getLabels(pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a label")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
