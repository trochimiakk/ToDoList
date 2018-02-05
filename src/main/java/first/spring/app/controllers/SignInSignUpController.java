package first.spring.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInSignUpController {

    @GetMapping("/login")
    public String singInSingUp(){
        return "singInSignUp";
    }

}
