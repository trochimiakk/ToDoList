package first.spring.app.dao;

import first.spring.app.models.TaskModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class TaskDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveTask(TaskModel task){
        sessionFactory.getCurrentSession().save(task);
    }

    public List<TaskModel> findTaskByUsername(String username) {
        List<TaskModel> taskList = sessionFactory.getCurrentSession()
                .createQuery("from TaskModel t where t.user.username=:username", TaskModel.class)
                .setParameter("username", username).list();
        return taskList;
    }

    public List<TaskModel> findTodaysTaskByUsername(String username) {
        LocalDateTime beginningOfToday = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime beginningOfTomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        List<TaskModel> taskList = sessionFactory.getCurrentSession()
                .createQuery("from TaskModel t where t.user.username =:username and t.date >= :beginningOfToday and t.date <= :beginningOfTomorrow order by t.date", TaskModel.class)
                .setParameter("username", username)
                .setParameter("beginningOfToday", beginningOfToday)
                .setParameter("beginningOfTomorrow", beginningOfTomorrow)
                .list();
        return taskList;
    }

    public Long countTasksByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("select count(*) from TaskModel t where t.user.username =:username", Long.class)
                .setParameter("username", username).getSingleResult();
    }

    public Long countCompletedTasksByUsername(String username) {
        return sessionFactory.getCurrentSession()
                .createQuery("select count(*) from TaskModel t where t.user.username =:username and t.done = true", Long.class)
                .setParameter("username", username).getSingleResult();
    }

    public List<TaskModel> findMissedTasksByUsername(String username) {
        LocalDateTime now = LocalDateTime.now();
        List<TaskModel> taskList = sessionFactory.getCurrentSession()
                .createQuery("from TaskModel t where t.user.username =:username and t.date < :now and t.done = false order by t.date", TaskModel.class)
                .setParameter("username", username)
                .setParameter("now", now)
                .list();
        return taskList;
    }

    public List<TaskModel> findFutureTasksByUsername(String username) {
        LocalDateTime beginningOfTomorrow = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        List<TaskModel> taskList = sessionFactory.getCurrentSession()
                .createQuery("from TaskModel t where t.user.username =:username and t.date >= :beginningOfTomorrow order by t.date", TaskModel.class)
                .setParameter("username", username)
                .setParameter("beginningOfTomorrow", beginningOfTomorrow)
                .list();
        return taskList;
    }
}
