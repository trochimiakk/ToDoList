package first.spring.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.spring.app.ajax.AjaxRequestParameters;
import first.spring.app.models.RoleModel;
import first.spring.app.models.TaskModel;
import first.spring.app.models.UserModel;
import first.spring.app.service.TaskService;
import first.spring.app.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cglib.core.Local;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.print.attribute.standard.Media;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    @InjectMocks
    TaskController taskControllerMock;

    @Mock
    TaskService taskServiceMock;

    @Mock
    Principal principal;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskControllerMock).build();
    }

    @Test
    public void shouldIncludeTaskModelAttributeAndReturnTaskCreationView() throws Exception {

        TaskModel expectedTaskAttribute = new TaskModel();

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("createTask"))
                .andExpect(model().attribute("task", expectedTaskAttribute));
    }

    @Test
    public void shouldAcceptTaskDataAndRedirectToTaskDetailsView() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        String principalName = "testUser";
        String taskTitle = "testTitle";
        String tastDescription = "taskDescription";
        String taskDone = "false";
        String taskDate = formatter.format(LocalDateTime.now().plusDays(2));
        TaskModel sessionAttribute = new TaskModel();
        long expectedTaskId = 0;

        when(principal.getName()).thenReturn(principalName);
        when(taskServiceMock.saveTask(any(TaskModel.class), anyString())).thenReturn(expectedTaskId);

        mockMvc.perform(post("/saveTask")
                .principal(principal)
                .param("title", taskTitle)
                .param("description", tastDescription)
                .param("done", taskDone)
                .param("date", taskDate)
                .sessionAttr("task", sessionAttribute))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/{username}/tasks/{taskId}/details"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("username", principalName))
                .andExpect(model().attribute("taskId", Long.toString(expectedTaskId)))
                .andExpect(flash().attributeExists("task"));
    }

    @Test
    public void shoulNotAcceptDateDataAndRedirectToTaskCreationView() throws Exception {
        LocalDateTime badlyFormattedDate = LocalDateTime.now().plusDays(2);
        String taskTitle = "testTitle";
        String tastDescription = "taskDescription";
        String taskDone = "false";
        String taskDate = badlyFormattedDate.toString();
        TaskModel sessionAttribute = new TaskModel();

        mockMvc.perform(post("/saveTask")
                .principal(principal)
                .param("title", taskTitle)
                .param("description", tastDescription)
                .param("done", taskDone)
                .param("date", taskDate)
                .sessionAttr("task", sessionAttribute))
                .andExpect(status().isOk())
                .andExpect(view().name("createTask"))
                .andExpect(model().attributeHasFieldErrors("task", "date"));
    }

    @Test
    public void shouldIncludeTodaysTasksListAttributeAndReturnTodaysTasksView() throws Exception {
        UserModel user = new UserModel("user", "user@gmail.com", "password", "password", true, new RoleModel("USER"));
        LocalDateTime date = LocalDateTime.now();
        TaskModel firstTask = new TaskModel(1, "title1", "description1", false, date, user);
        TaskModel secondTask = new TaskModel(2, "title2", "description2", false, date, user);

        List<TaskModel> expectedTasks = Arrays.asList(firstTask, secondTask);

        when(taskServiceMock.findTodaysTaskByUsername(anyString())).thenReturn(expectedTasks);

        mockMvc.perform(get("/users/{username}/tasks/today", "user"))
                .andExpect(status().isOk())
                .andExpect(view().name("todaysTasks"))
                .andExpect(model().attribute("todaysTasksList", expectedTasks));

    }

    @Test
    public void shouldIncludeFutureTasksAndMissedTasksAttributesAndReturnOtherTasksView() throws Exception {
        UserModel user = new UserModel("user", "user@gmail.com", "password", "password", true, new RoleModel("USER"));
        LocalDateTime pastDate = LocalDateTime.now().minusDays(10);
        LocalDateTime futureDate = LocalDateTime.now().plusDays(10);
        TaskModel missedTask = new TaskModel(1, "title1", "description1", false, pastDate, user);
        TaskModel futureTask = new TaskModel(2, "title2", "description2", false, futureDate, user);

        Map<String, List<TaskModel>> expectedTasks = new HashMap<>();
        expectedTasks.put("missedTasks", Arrays.asList(missedTask));
        expectedTasks.put("futureTasks", Arrays.asList(futureTask));

        when(taskServiceMock.findNotTodaysTaskByUsername(anyString())).thenReturn(expectedTasks);


        mockMvc.perform(get("/users/{username}/tasks/other", "user"))
                .andExpect(status().isOk())
                .andExpect(view().name("otherTasks"))
                .andExpect(model().attribute("missedTasks", expectedTasks.get("missedTasks")))
                .andExpect(model().attribute("futureTasks", expectedTasks.get("futureTasks")));

    }

    @Test
    public void shouldIncludeTaskAttributeAndReturnTaskDetailsView() throws Exception {
        UserModel user = new UserModel("user", "user@gmail.com", "password", "password", true, new RoleModel("USER"));
        LocalDateTime date = LocalDateTime.now().plusDays(10);
        TaskModel expectedTask = new TaskModel(1, "title1", "description1", false, date, user);

        when(taskServiceMock.findTaskById(anyLong())).thenReturn(expectedTask);

        mockMvc.perform(get("/users/{username}/tasks/{taskId}/details", "user", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("taskDetails"))
                .andExpect(model().attribute("task", expectedTask));
    }

    @Test
    public void shouldReturnTasksStatusChangedSuccessfullyResponse() throws Exception {
        AjaxRequestParameters ajaxRequestParameters = new AjaxRequestParameters(1);

        doNothing().when(taskServiceMock).updateTaskStatus(anyLong(), anyString());

        mockMvc.perform(put("/markTaskAsDone")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ajaxRequestParameters)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("The task's status was changed successfully.")));
    }

    @Test
    public void shouldReturnBadRequestForInvalidPutContent() throws Exception {

        String invalidContent = "taskId: 1";

        mockMvc.perform(put("/markTaskAsDone")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnTaskDeletedSuccessfullyResponse() throws Exception {
        AjaxRequestParameters ajaxRequestParameters = new AjaxRequestParameters(1);

        doNothing().when(taskServiceMock).deleteTask(anyLong(), anyString());

        mockMvc.perform(delete("/deleteTask")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ajaxRequestParameters)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("The task was deleted successfully.")));
    }

    @Test
    public void shouldReturnBadRequestForInvalidDeleteContent() throws Exception {

        String invalidContent = "taskId: 1";

        mockMvc.perform(delete("/deleteTask")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidContent))
                .andExpect(status().isBadRequest());
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(object);
        return jsonContent;
    }

}
