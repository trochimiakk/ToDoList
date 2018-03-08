package first.spring.app.service;

import first.spring.app.dao.RoleDao;
import first.spring.app.models.RoleModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    RoleService roleServiceMock;

    @Mock
    RoleDao roleDaoMock;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(roleServiceMock).build();
    }

    @Test
    public void shouldReturnRole() {

        RoleModel expectedRole = new RoleModel("USER");

        when(roleDaoMock.getBasicRole()).thenReturn(expectedRole);

        assertEquals(expectedRole, roleServiceMock.getBasicRole());

        verify(roleDaoMock, times(1)).getBasicRole();

    }
}
