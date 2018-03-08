package first.spring.app.controllers;

import first.spring.app.ajax.AjaxRequestParameters;
import first.spring.app.exception.TaskNotFoundException;
import first.spring.app.json.JsonResponse;
import first.spring.app.models.TaskModel;
import first.spring.app.service.TaskService;
import first.spring.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public String createTask(Model model){
        model.addAttribute("task", new TaskModel());
        return "createTask";
    }

    @PostMapping("/saveTask")
    public String saveTask(Principal principal, @ModelAttribute("task") @Valid TaskModel taskModel, Errors errors, RedirectAttributes model) {
        if (errors.hasErrors()){
            return "createTask";
        }

        long taskId = taskService.saveTask(taskModel, principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("taskId", taskId);
        model.addFlashAttribute("task", taskModel);
        return "redirect:/users/{username}/tasks/{taskId}/details";
    }

    @GetMapping("/users/{username}/tasks/today")
    public String todaysTasks(@PathVariable("username") String username, Model model){

        model.addAttribute("todaysTasksList", taskService.findTodaysTaskByUsername(username));
        return "todaysTasks";

    }

    @GetMapping("/users/{username}/tasks/other")
    public String notTodaysTasks(@PathVariable("username") String username, Model model){

        Map<String, List<TaskModel>> otherTasks = taskService.findNotTodaysTaskByUsername(username);
        model.addAttribute("missedTasks", otherTasks.get("missedTasks"));
        model.addAttribute("futureTasks", otherTasks.get("futureTasks"));
        return "otherTasks";

    }

    @GetMapping("users/{username}/tasks/{taskId}/details")
    public String taskDetails(@PathVariable("taskId") long taskId, Model model) throws TaskNotFoundException {

        if (!model.containsAttribute("task")){
            model.addAttribute("task", taskService.findTaskById(taskId));
        }

        return "taskDetails";
    }

    @DeleteMapping("/deleteTask")
    @ResponseBody
    public ResponseEntity<JsonResponse> deleteTask(Principal principal, @RequestBody AjaxRequestParameters ajaxRequestParameters){
        taskService.deleteTask(ajaxRequestParameters.getTaskId(), principal.getName());
        JsonResponse response = new JsonResponse("The task was deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/markTaskAsDone")
        @ResponseBody
        public ResponseEntity<JsonResponse> markTaskAsDone(Principal principal, @RequestBody AjaxRequestParameters ajaxRequestParameters){
            taskService.updateTaskStatus(ajaxRequestParameters.getTaskId(), principal.getName());
            JsonResponse response = new JsonResponse("The task's status was changed successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
