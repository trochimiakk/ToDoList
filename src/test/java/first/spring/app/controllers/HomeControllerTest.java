package first.spring.app.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        HomeController homeController = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void shouldReturnHomeViewName() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}
