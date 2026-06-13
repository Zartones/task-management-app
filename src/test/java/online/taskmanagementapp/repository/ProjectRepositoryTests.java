package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.Project;
import online.taskmanagementapp.models.ProjectStatus;
import online.taskmanagementapp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTests {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Project project;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("Username");
        userRepository.save(user);

        project = new Project();
        project.setName("Name");
        project.setDescription("Description");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusMonths(1));
        project.setStatus(ProjectStatus.INITIATED);
        project.setUser(user);
        projectRepository.save(project);
    }

    @Test
    @DisplayName("find all projects by user id")
    void findAllByUserId_ExistingUser_ReturnsPage() {
        Page<Project> result = projectRepository
                .findAllByUserId(user.getId(), PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Name");
        assertThat(result.getContent().get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("find projects by non existing user id")
    void findAllByUserId_NonExistingUser_ReturnsEmptyPage() {
        Page<Project> result = projectRepository
                .findAllByUserId(50L, PageRequest.of(0, 10));

        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("find project by id and user id")
    void findByUserIdAndId_MatchingIds_ReturnsProject() {
        Optional<Project> result = projectRepository
                .findByUserIdAndId(user.getId(), project.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Name");
        assertThat(result.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("find project by fake id and true user id")
    void findByUserIdAndId_WrongProjectId_ReturnsEmpty() {
        Optional<Project> result = projectRepository
                .findByUserIdAndId(user.getId(), 50L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("delete project by id and user id")
    @Transactional
    void deleteByUserIdAndId_MatchingIds_DeletesProject() {
        projectRepository.deleteByUserIdAndId(user.getId(), project.getId());

        assertThat(projectRepository.findById(project.getId())).isEmpty();
    }

    @Test
    @DisplayName("delete project by fake user id")
    @Transactional
    void deleteByUserIdAndId_WrongUserId_DoesNotDelete() {
        projectRepository.deleteByUserIdAndId(50L, project.getId());

        assertThat(projectRepository.findById(project.getId())).isPresent();
    }
}
