package first.spring.app.controllers;

import first.spring.app.models.TaskModel;
import first.spring.app.models.UserModel;
import first.spring.app.service.TaskService;
import first.spring.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public String createTask(Model model){
        model.addAttribute("task", new TaskModel());
        return "createTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(Principal principal, @ModelAttribute("task") @Valid TaskModel taskModel, Errors errors){
        if (errors.hasErrors()){
            return "createTask";
        }

        UserModel userModel = userService.findUserByUsername(principal.getName());
        taskModel.setUser(userModel);
        taskService.saveTask(taskModel);

        return "redirect:/";
    }

    @GetMapping("/users/{username}/tasks/today")
    public String tasks(@PathVariable("username") String username, Model model){

        model.addAttribute("taskList", taskService.findTodaysTaskByUsername(username));
        return "todaysTasks";

    }

}
