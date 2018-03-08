package first.spring.app.service;

import first.spring.app.dao.TaskDao;
import first.spring.app.exception.TaskNotFoundException;
import first.spring.app.models.TaskModel;
import first.spring.app.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Transactional
    public long saveTask(TaskModel task, String username) {
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        task.setUser(userModel);
        return taskDao.saveTask(task);
    }

    @Transactional
    @PreAuthorize("#username == principal.username")
    public List<TaskModel> findTodaysTaskByUsername(String username) {
        return taskDao.findTodaysTaskByUsername(username);
    }

    @Transactional
    @PreAuthorize("#username == principal.username")
    public Map<String, List<TaskModel>> findNotTodaysTaskByUsername(String username) {
        Map<String, List<TaskModel>> otherTasks = new HashMap<>();
        otherTasks.put("missedTasks", taskDao.findMissedTasksByUsername(username));
        otherTasks.put("futureTasks", taskDao.findFutureTasksByUsername(username));

        return otherTasks;
    }

    @Transactional
    @PreAuthorize("#username == principal.username")
    public Map<String,Long> createUserStats(String username) {
        Map<String, Long> userStats = new HashMap<>();
        userStats.put("allCreatedTasks", taskDao.countTasksByUsername(username));
        userStats.put("allCompletedTasks", taskDao.countCompletedTasksByUsername(username));

        return userStats;
    }

    @Transactional
    @PostAuthorize("returnObject.user.username == principal.username")
    public TaskModel findTaskById(long taskId) throws TaskNotFoundException {
        return taskDao.findTaskById(taskId);
    }

    @Transactional
    @PreAuthorize("@taskService.checkIfUserIsTaskOwner(#taskId, #username)")
    public int deleteTask(long taskId, String username) {
        return taskDao.deleteTask(taskId);
    }

    @Transactional
    @PreAuthorize("@taskService.checkIfUserIsTaskOwner(#taskId, #username)")
    public int updateTaskStatus(long taskId, String username) {
        return taskDao.updateTask(taskId);
    }

    public boolean checkIfUserIsTaskOwner(long taskId, String username){
        TaskModel taskToCheck = null;
        try {
            taskToCheck = findTaskById(taskId);
        } catch (TaskNotFoundException e) {
            return false;
        }
        return taskToCheck.getUser().getUsername().equals(username);
    }
}
