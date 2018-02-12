package first.spring.app.controllers;

import first.spring.app.models.UserModel;
import first.spring.app.service.UserService;
import first.spring.app.validators.RegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignInSignUpController {

    @Autowired
    RegistrationFormValidator registrationFormValidator;

    @Autowired
    UserService userService;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(registrationFormValidator);
    }

    @GetMapping("/login")
    public ModelAndView signInSignUp(@RequestParam(value = "error", required = false) String error,
                                     @RequestParam(value = "logout", required = false) String logout,
                                     @RequestParam(value = "registered", required = false) String registered){
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("loginError", "Invalid username or password!");
        }else if (logout != null) {
            model.addObject("loggedOut", "You've been logged out successfully.");
        } else if (registered != null) {
            model.addObject("registered", "Your account has been successfully created.");
        }

        model.setViewName("signInSignUp");
        model.addObject("user", new UserModel());

        return model;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserModel userModel, Errors errors){
        if (errors.hasErrors()){
            return "signInSignUp";
        }

        userService.saveUser(userModel);

        return "redirect:login?registered";
    }

}
