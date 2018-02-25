package first.spring.app.service;

import first.spring.app.dao.TaskDao;
import first.spring.app.models.TaskModel;
import first.spring.app.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    UserService userService;

    @Autowired
    TaskDao taskDao;

    @Transactional
    public long saveTask(TaskModel task, String username){
        UserModel userModel = userService.findUserByUsername(username);
        task.setUser(userModel);
        return taskDao.saveTask(task);
    }

    @Transactional
    public List<TaskModel> findTaskByUsername(String username){
        return taskDao.findTaskByUsername(username);
    }

    @Transactional
    public List<TaskModel> findTodaysTaskByUsername(String username) {
        return taskDao.findTodaysTaskByUsername(username);
    }

    @Transactional
    public Map<String, List<TaskModel>> findNotTodaysTaskByUsername(String username) {
        Map<String, List<TaskModel>> otherTasks = new HashMap<>();
        otherTasks.put("missedTasks", taskDao.findMissedTasksByUsername(username));
        otherTasks.put("futureTasks", taskDao.findFutureTasksByUsername(username));

        return otherTasks;
    }

    @Transactional
    public Map<String,Long> createUserStats(String username) {
        Map<String, Long> userStats = new HashMap<>();
        userStats.put("allCreatedTasks", taskDao.countTasksByUsername(username));
        userStats.put("allCompletedTasks", taskDao.countCompletedTasksByUsername(username));

        return userStats;
    }

    @Transactional
    public TaskModel findTaskById(long taskId) {
        return taskDao.findTaskById(taskId);
    }

    @Transactional
    public void deleteTask(TaskModel task) {
        taskDao.deleteTask(task);
    }

    @Transactional
    public void updateTaskStatus(TaskModel task) {
        taskDao.updateTask(task);
    }
}
