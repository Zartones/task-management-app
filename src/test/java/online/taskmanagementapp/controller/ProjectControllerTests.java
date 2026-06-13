package online.taskmanagementapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import online.taskmanagementapp.dto.project.ProjectRequestDto;
import online.taskmanagementapp.dto.project.ProjectResponseDto;
import online.taskmanagementapp.util.TestUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTests {

    private static final String BASE_URL = "/projects";

    protected static MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("test@gmail.com")
    @DisplayName("Create a project")
    @Sql(scripts = "classpath:database/add-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-projects.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createProject_AuthenticatedUser_Returns201() throws Exception {
        ProjectRequestDto req = TestUtil.sampleRequestDto();

        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();

        ProjectResponseDto expected = TestUtil.sampleResponseDto();

        ProjectResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProjectResponseDto.class
        );

        assertThat(result.getResponse().getStatus()).isEqualTo(201);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }


    @Test
    @WithUserDetails("test@gmail.com")
    @DisplayName("Get user's projects")
    @Sql(scripts = "classpath:database/add-projects.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-projects.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getUserProjects_AuthenticatedUser_Returns200() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL))
                .andReturn();

        ProjectResponseDto expected = TestUtil.sampleResponseDto();

        String json = result.getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(json);
        JsonNode firstProject = root.get("content").get(0);
        ProjectResponseDto actual = objectMapper.treeToValue(firstProject, ProjectResponseDto.class);

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithUserDetails("test@gmail.com")
    @DisplayName("Get project by id")
    @Sql(scripts = "classpath:database/add-projects.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-projects.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getProjectById_ExistingId_Returns200() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL + "/1"))
                .andReturn();

        ProjectResponseDto expected = TestUtil.sampleResponseDto();

        ProjectResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProjectResponseDto.class
        );

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("\"id\":1");
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithUserDetails("test@gmail.com")
    @DisplayName("Update a project")
    @Sql(scripts = "classpath:database/add-projects.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-projects.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateProject_ExistingId_Returns200() throws Exception {
        ProjectRequestDto req = TestUtil.sampleRequestDto();
        req.setName("Updated Project");

        MvcResult result = mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andReturn();

        ProjectResponseDto expected = TestUtil.sampleResponseDto();
        expected.setName("Updated Project");

        ProjectResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ProjectResponseDto.class
        );

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    @WithUserDetails(value = "test@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("Delete a project")
    @Sql(scripts = "classpath:database/add-projects.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-projects.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteProject_ExistingId_Returns204() throws Exception {
        MvcResult result = mockMvc.perform(delete(BASE_URL + "/1"))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(204);
        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }
}