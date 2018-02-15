package first.spring.app.service;

import first.spring.app.dao.TaskDao;
import first.spring.app.models.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Transactional
    public void saveTask(TaskModel task){
        taskDao.saveTask(task);
    }
}
