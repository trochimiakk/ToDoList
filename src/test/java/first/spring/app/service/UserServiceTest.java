package first.spring.app.service;

import first.spring.app.dao.UserDao;
import first.spring.app.exception.UserNotFoundException;
import first.spring.app.models.RoleModel;
import first.spring.app.models.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    UserService userServiceMock;

    @Mock
    UserDao userDaoMock;

    @Mock
    RoleService roleServiceMock;

    private UserModel expectedUser;

    private RoleModel expectedUserRole;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userDaoMock).build();
        expectedUserRole = new RoleModel("USER");
        expectedUser = new UserModel("username", "test@gmail.com", "password", "password", true, expectedUserRole);
    }

    @Test
    public void shouldFindUserByEmail() throws UserNotFoundException {

        when(userDaoMock.findUserByEmail(anyString())).thenReturn(expectedUser);
        assertEquals(expectedUser, userServiceMock.findUserByEmail(anyString()));
        verify(userDaoMock, times(1)).findUserByEmail(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFindUserByEmailAndThrowUserNotFindException() throws UserNotFoundException {

        when(userDaoMock.findUserByEmail(anyString())).thenThrow(UserNotFoundException.class);
        userServiceMock.findUserByEmail(anyString());
        verify(userDaoMock, times(1)).findUserByEmail(anyString());
    }

    @Test
    public void shouldFindUserByUsername() throws UserNotFoundException {

        when(userDaoMock.findUserByUsername(anyString())).thenReturn(expectedUser);
        assertEquals(expectedUser, userServiceMock.findUserByUsername(anyString()));
        verify(userDaoMock, times(1)).findUserByUsername(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFindUserByUsernameAndThrowUserNotFindException() throws UserNotFoundException {

        when(userDaoMock.findUserByUsername(anyString())).thenThrow(UserNotFoundException.class);
        userServiceMock.findUserByUsername(anyString());
        verify(userDaoMock, times(1)).findUserByUsername(anyString());
    }

    @Test
    public void shouldSaveUser() {

        when(roleServiceMock.getBasicRole()).thenReturn(expectedUserRole);
        doNothing().when(userDaoMock).saveUser(expectedUser);
        userServiceMock.saveUser(expectedUser);

        verify(roleServiceMock, times(1)).getBasicRole();
        verify(userDaoMock, times(1)).saveUser(expectedUser);
    }

    @Test
    public void shouldReturnFalseForNotUsedEmail() throws UserNotFoundException {

        String notUsedEmail = "notUsedEmail@gmail.com";

        when(userDaoMock.findUserByEmail(notUsedEmail)).thenThrow(UserNotFoundException.class);
        assertEquals(false, userServiceMock.checkIfUserWithEmailAlreadyExists(notUsedEmail));

        verify(userDaoMock, times(1)).findUserByEmail(notUsedEmail);
    }

    @Test
    public void shouldReturnTrueForAlreadyUsedEmail() throws UserNotFoundException {

        String alreadyUsedEmail = "alreadyUsedEmail@gmail.com";

        when(userDaoMock.findUserByEmail(alreadyUsedEmail)).thenReturn(expectedUser);
        assertEquals(true, userServiceMock.checkIfUserWithEmailAlreadyExists(alreadyUsedEmail));

        verify(userDaoMock, times(1)).findUserByEmail(alreadyUsedEmail);
    }

    @Test
    public void shouldReturnFalseForNotUsedUsername() throws UserNotFoundException {

        String notUsedUsername = "notUsedUsername";

        when(userDaoMock.findUserByUsername(notUsedUsername)).thenThrow(UserNotFoundException.class);
        assertEquals(false, userServiceMock.checkIfUserWithUsernameAlreadyExists(notUsedUsername));

        verify(userDaoMock, times(1)).findUserByUsername(notUsedUsername);
    }

    @Test
    public void shouldReturnTrueForAlreadyUsedUsername() throws UserNotFoundException {

        String alreadyUsedUsername = "alreadyUsedUsername";

        when(userDaoMock.findUserByUsername(alreadyUsedUsername)).thenReturn(expectedUser);
        assertEquals(true, userServiceMock.checkIfUserWithUsernameAlreadyExists(alreadyUsedUsername));

        verify(userDaoMock, times(1)).findUserByUsername(alreadyUsedUsername);
    }
}
