package first.spring.app.dao;

import first.spring.app.models.TaskModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveTask(TaskModel task){
        sessionFactory.getCurrentSession().save(task);
    }

}
