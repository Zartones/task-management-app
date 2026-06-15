package online.taskmanagementapp.dto.user;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Data;
import online.taskmanagementapp.models.Role;

@Data
public class UserRolesRequestDto {
    @NotEmpty
    private Set<Role> roles;
}
