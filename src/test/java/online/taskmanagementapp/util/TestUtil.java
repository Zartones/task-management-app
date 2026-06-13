package online.taskmanagementapp.util;

import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import online.taskmanagementapp.models.ProjectStatus;

import java.time.LocalDate;

public class TestUtil {
    public static ProjectRequestDto sampleRequestDto() {
        ProjectRequestDto dto = new ProjectRequestDto();
        dto.setName("Test Project");
        dto.setDescription("Test Description");
        dto.setStartDate(LocalDate.of(2025, 1, 1));
        dto.setEndDate(LocalDate.of(2025, 6, 1));
        dto.setStatus(ProjectStatus.INITIATED);
        return dto;
    }

    public static ProjectResponseDto sampleResponseDto() {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setName("Test Project");
        dto.setDescription("Test Description");
        dto.setStartDate(LocalDate.of(2025, 1, 1));
        dto.setEndDate(LocalDate.of(2025, 6, 1));
        dto.setStatus(ProjectStatus.INITIATED);
        return dto;
    }

}
