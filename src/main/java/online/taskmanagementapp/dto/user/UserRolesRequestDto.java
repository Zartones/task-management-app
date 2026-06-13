package online.taskmanagementapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;
import online.taskmanagementapp.models.Role;

@Data
public class UserRolesRequestDto {
    @NotBlank
    private Set<Role> roles;
}
