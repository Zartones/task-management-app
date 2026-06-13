package online.taskmanagementapp.repository;

import online.taskmanagementapp.models.*;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private Project project;
    private Task task;

    @BeforeEach
    void setUp() {
        User user = new User();
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

        task = new Task();
        task.setName("Name");
        task.setDescription("Description");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setDueDate(LocalDate.now().plusWeeks(1));
        task.setProject(project);
        task.setAssignee(user);
        taskRepository.save(task);
    }

    @Test
    @DisplayName("find tasks by project id")
    void findAllByProjectId_ExistingProject_ReturnsPage() {
        Page<Task> result = taskRepository
                .findAllByProjectId(project.getId(), PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Name");
        assertThat(result.getContent().get(0).getProject().getId()).isEqualTo(project.getId());
    }

    @Test
    @DisplayName("find all tasks by fake project id")
    void findAllByProjectId_NonExistingProject_ReturnsEmptyPage() {
        Page<Task> result = taskRepository
                .findAllByProjectId(50L, PageRequest.of(0, 10));

        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("delete task by id")
    @Transactional
    void deleteById_ValidId_DeletesTask() {
        taskRepository.deleteById(task.getId());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }

    @Test
    @DisplayName("delete task by fake id")
    @Transactional
    void deleteById_NonExistingId_DoesNothing() {
        taskRepository.deleteById(50L);

        assertThat(taskRepository.findById(task.getId())).isPresent();
    }
}
