package first.spring.app.controllers;

import first.spring.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class UserProfileController {

    @Autowired
    TaskService taskService;

    @GetMapping("/users/{username}")
    public String userProfile(@PathVariable("username") String username, Model model){
        Map<String, Long> userStats = taskService.createUserStats(username);
        model.addAttribute("allCreatedTasks", userStats.get("allCreatedTasks"));
        model.addAttribute("allCompletedTasks", userStats.get("allCompletedTasks"));
        return "userProfile";
    }
}
