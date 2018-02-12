package first.spring.app.validators;

import first.spring.app.models.UserModel;
import first.spring.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator{

    @Autowired
    UserService userService;

    public boolean supports(Class<?> aClass) {
        return UserModel.class.equals(aClass);
    }

    public void validate(Object obj, Errors errors) {
        UserModel userModel = (UserModel) obj;

        if (userService.findUserByEmail(userModel.getEmail().toLowerCase()) != null){
            errors.reject("email", "User with this email already exists.");
        }

        if (userService.findUserByUsername(userModel.getUsername().toLowerCase()) != null){
            errors.reject("username", "User with this username already exists.");
        }

        if (!userModel.getPassword().equals(userModel.getConfirmPassword())){
            errors.reject("confirmPassword", "Password confirmation doesn't match password");
        }
    }
}
