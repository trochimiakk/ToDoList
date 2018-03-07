package first.spring.app.controllers;

import first.spring.app.models.UserModel;
import first.spring.app.service.UserService;
import first.spring.app.validators.RegistrationFormValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SignInSignUpControllerTest {

    @InjectMocks
    SignInSignUpController signInSignUpController;

    @Mock
    UserService userServiceMock;

    @Mock
    RegistrationFormValidator registrationFormValidatorMock;


    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signInSignUpController).build();
        when(registrationFormValidatorMock.supports(any(Class.class))).thenReturn(true);
    }

    @Test
    public void shouldlIncludeUserModelAttributeAndReturnSignInSignUpViewName() throws Exception {
        UserModel expectedUserAttribute = new UserModel();

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("signInSignUp"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", expectedUserAttribute));
    }

    @Test
    public void shouldlIncludeRegisteredAttributeAndReturnSignInSignUpViewName() throws Exception {
        String expectedRegisteredAttribute = "Your account has been successfully created.";

        mockMvc.perform(get("/login").param("registered", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("signInSignUp"))
                .andExpect(model().attributeExists("user", "registered"))
                .andExpect(model().attribute("registered", expectedRegisteredAttribute));
    }

    @Test
    public void shouldlIncludeLoggedOutAttributeAndReturnSignInSignUpViewName() throws Exception {
        String expectedLoggedOutAttribute = "You've been logged out successfully.";

        mockMvc.perform(get("/login").param("logout", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("signInSignUp"))
                .andExpect(model().attributeExists("user", "loggedOut"))
                .andExpect(model().attribute("loggedOut", expectedLoggedOutAttribute));
    }

    @Test
    public void shouldlIncludeLoginErrorAttributeAndReturnSignInSignUpViewName() throws Exception {
        String expectedLoginErrorAttribute = "Invalid username or password!";

        mockMvc.perform(get("/login").param("error", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("signInSignUp"))
                .andExpect(model().attributeExists("user", "loginError"))
                .andExpect(model().attribute("loginError", expectedLoginErrorAttribute));
    }

    @Test
    public void shouldAcceptUserDataAndRedirectToLoginPage() throws Exception {
        String username = "testUser";
        String email = "test@gmail.com";
        String password = "password";
        String confirmPassword = "password";
        UserModel sessionAttribute = new UserModel();

        doNothing().when(userServiceMock).saveUser(any(UserModel.class));

        mockMvc.perform(post("/register")
                .param("username", username)
                .param("email", email)
                .param("password", password)
                .param("confirmPassword", confirmPassword)
                .sessionAttr("user", sessionAttribute))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeHasNoErrors())
                .andExpect(view().name("redirect:login?registered"));
    }

    @Test
    public void shouldNotAcceptUsernameDataAndReturnSignInSignUpView() throws Exception {

        String alreadyUsedUsername = "alreadyUsedUsername";
        String email = "test@gmail.com";
        String password = "password";
        String confirmPassword = "password";
        UserModel sessionAttribute = new UserModel();

        doAnswer(invocationOnMock -> {
            Errors errors = invocationOnMock.getArgument(1);
            errors.rejectValue("username", "User with this username already exists.");
            return null;
        }).when(registrationFormValidatorMock).validate(any(UserModel.class), any(Errors.class));

        mockMvc.perform(post("/register")
                .param("username", alreadyUsedUsername)
                .param("email", email)
                .param("password", password)
                .param("confirmPassword", confirmPassword)
                .sessionAttr("user", sessionAttribute))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(view().name("signInSignUp"));
    }
}
