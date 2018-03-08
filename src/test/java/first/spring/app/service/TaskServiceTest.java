package first.spring.app.service;

import first.spring.app.dao.TaskDao;
import first.spring.app.exception.TaskNotFoundException;
import first.spring.app.models.RoleModel;
import first.spring.app.models.TaskModel;
import first.spring.app.models.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    TaskService taskServiceMock;

    @Mock
    TaskDao taskDaoMock;

    private UserModel user;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(taskServiceMock).build();

        user = new UserModel("username", "email@gmail.com", "password", "password", true, new RoleModel("USER"));
    }

    @Test
    public void shouldSaveTaskAndReturnReturnId() {
        TaskModel taskToSave = new TaskModel();
        taskToSave.setTitle("title");
        taskToSave.setDone(false);
        taskToSave.setDate(LocalDateTime.now());
        long expectedTaskId = 1;

        when(taskDaoMock.saveTask(any(TaskModel.class))).thenReturn(expectedTaskId);

        assertEquals(expectedTaskId, taskServiceMock.saveTask(taskToSave, anyString()));
        verify(taskDaoMock, times(1)).saveTask(any(TaskModel.class));
    }

    @Test
    public void shouldReturnListOfTodaysUserTasks() {

        TaskModel firstTask = new TaskModel(1, "title1", "description1", false, LocalDateTime.now().plusMinutes(20), user);
        TaskModel secondTask = new TaskModel(2, "title2", "description2", true, LocalDateTime.now().minusMinutes(5), user);
        List<TaskModel> expectedTasks = Arrays.asList(firstTask, secondTask);

        when(taskDaoMock.findTodaysTaskByUsername(user.getUsername())).thenReturn(expectedTasks);

        assertEquals(expectedTasks, taskServiceMock.findTodaysTaskByUsername(user.getUsername()));
        verify(taskDaoMock, times(1)).findTodaysTaskByUsername(user.getUsername());
    }

    @Test
    public void shouldReturnListOfNotTodaysUserTasks() {

        TaskModel missedTask = new TaskModel(1, "title1", "description1", false, LocalDateTime.now().minusMinutes(20), user);
        TaskModel futureTask = new TaskModel(2, "title2", "description2", false, LocalDateTime.now().plusMinutes(50), user);
        Map<String, List<TaskModel>> expectedTasks = new HashMap<>();
        expectedTasks.put("missedTasks", Arrays.asList(missedTask));
        expectedTasks.put("futureTasks", Arrays.asList(futureTask));

        when(taskDaoMock.findMissedTasksByUsername(user.getUsername())).thenReturn(Arrays.asList(missedTask));
        when(taskDaoMock.findFutureTasksByUsername(user.getUsername())).thenReturn(Arrays.asList(futureTask));

        assertEquals(expectedTasks, taskServiceMock.findNotTodaysTaskByUsername(user.getUsername()));
        verify(taskDaoMock, times(1)).findMissedTasksByUsername(user.getUsername());
        verify(taskDaoMock, times(1)).findFutureTasksByUsername(user.getUsername());
    }

    @Test
    public void shouldReturnMapWithNumberOfCreatedAndNumberOfCompletedUserTasks() {
        Map<String, Long> expectedUserStats = new HashMap<>();
        Long createdTasks = new Long(10);
        long completedTasks = new Long(5);
        expectedUserStats.put("allCreatedTasks", createdTasks);
        expectedUserStats.put("allCompletedTasks", completedTasks);

        when(taskDaoMock.countTasksByUsername(user.getUsername())).thenReturn(createdTasks);
        when(taskDaoMock.countCompletedTasksByUsername(user.getUsername())).thenReturn(completedTasks);

        assertEquals(expectedUserStats, taskServiceMock.createUserStats(user.getUsername()));
        verify(taskDaoMock, times(1)).countTasksByUsername(user.getUsername());
        verify(taskDaoMock, times(1)).countCompletedTasksByUsername(user.getUsername());
    }

    @Test
    public void shouldFindTaskById() throws TaskNotFoundException {

        TaskModel expectedTask = new TaskModel(1, "title", "description", false, LocalDateTime.now(), user);

        when(taskDaoMock.findTaskById(expectedTask.getId())).thenReturn(expectedTask);

        assertEquals(expectedTask, taskServiceMock.findTaskById(expectedTask.getId()));
        verify(taskDaoMock, times(1)).findTaskById(expectedTask.getId());
    }

    @Test(expected = TaskNotFoundException.class)
    public void shouldNotFindTaskByIdAndThrowTaskNotFoundException() throws TaskNotFoundException {

        when(taskDaoMock.findTaskById(anyLong())).thenThrow(TaskNotFoundException.class);

        taskServiceMock.findTaskById(anyLong());
        verify(taskDaoMock, times(1)).findTaskById(anyLong());
    }

    @Test
    public void shouldUpdateTaskStatusAndReturnNumberOfUpdatedDatabaseRows() {

        long taskId = 1;
        String username = "username";
        int expectedNumberOfUpdatedRows = 1;

        when(taskDaoMock.updateTask(taskId)).thenReturn(expectedNumberOfUpdatedRows);

        assertEquals(expectedNumberOfUpdatedRows, taskServiceMock.updateTaskStatus(taskId, username));
        verify(taskDaoMock, times(1)).updateTask(taskId);
    }

    @Test
    public void shouldDeleteTaskAndReturnNumberOfDeletedDatabaseRows() {

        long taskId = 1;
        String username = "username";
        int expectedNumberOfUDeletedRows = 1;

        when(taskDaoMock.deleteTask(taskId)).thenReturn(expectedNumberOfUDeletedRows);

        assertEquals(expectedNumberOfUDeletedRows, taskServiceMock.deleteTask(taskId, username));
        verify(taskDaoMock, times(1)).deleteTask(taskId);
    }

    @Test
    public void shouldIndicateUserIsTaskOwner() throws TaskNotFoundException {

        TaskModel taskToCheck = new TaskModel(1, "title", "description", false, LocalDateTime.now(), user);
        String taskOwner = taskToCheck.getUser().getUsername();

        when(taskDaoMock.findTaskById(taskToCheck.getId())).thenReturn(taskToCheck);

        assertEquals(true, taskServiceMock.checkIfUserIsTaskOwner(taskToCheck.getId(), taskOwner));
        verify(taskDaoMock, times(1)).findTaskById(taskToCheck.getId());
    }

    @Test
    public void shouldNotIndicateUserIsTaskOwner() throws TaskNotFoundException {

        TaskModel taskToCheck = new TaskModel(1, "title", "description", false, LocalDateTime.now(), user);
        String otherUser = taskToCheck.getUser().getUsername() + "randomText";

        when(taskDaoMock.findTaskById(taskToCheck.getId())).thenReturn(taskToCheck);

        assertEquals(false, taskServiceMock.checkIfUserIsTaskOwner(taskToCheck.getId(), otherUser));
        verify(taskDaoMock, times(1)).findTaskById(taskToCheck.getId());
    }
}
