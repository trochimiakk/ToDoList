package first.spring.app.controllers;

import first.spring.app.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserProfileControllerTest {

    @InjectMocks
    UserProfileController userProfileControllerMock;

    @Mock
    TaskService taskServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileControllerMock).build();
    }

    @Test
    public void shouldIncludeAllCreatedTasksAndAllCompletedTasksAttributesAndReturnUserProfileView() throws Exception {

        String username = "test";
        Map<String, Long> expectedUserStats = new HashMap<>();
        expectedUserStats.put("allCreatedTasks", new Long(10));
        expectedUserStats.put("allCompletedTasks", new Long(4));

        when(taskServiceMock.createUserStats(username)).thenReturn(expectedUserStats);

        mockMvc.perform(get("/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(view().name("userProfile"))
                .andExpect(model().attributeExists("allCreatedTasks", "allCompletedTasks"))
                .andExpect(model().attribute("allCreatedTasks", expectedUserStats.get("allCreatedTasks")))
                .andExpect(model().attribute("allCompletedTasks", expectedUserStats.get("allCompletedTasks")));

    }
}
