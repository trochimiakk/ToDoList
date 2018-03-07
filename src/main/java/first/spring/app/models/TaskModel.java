package first.spring.app.models;

import javafx.concurrent.Task;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    @Length(min = 3, max = 20, message = "{task.title.length}")
    private  String title;

    @Column(name = "description", nullable = false)
    @Length(max = 1000, message = "{task.description.length}")
    private String description;

    @Column(name = "done", nullable = false)
    private boolean done;

    @Column(name = "date", nullable = false)
    @Future(message = "{task.date.future}")
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private UserModel user;

    public TaskModel(){ }

    public TaskModel(long id, String title, String description, boolean done, LocalDateTime date, UserModel user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
        this.date = date;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        return date.format(formatter);
    }

    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return date.format(formatter);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return id == taskModel.id &&
                done == taskModel.done &&
                Objects.equals(title, taskModel.title) &&
                Objects.equals(description, taskModel.description) &&
                Objects.equals(date, taskModel.date) &&
                Objects.equals(user, taskModel.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, description, done, date, user);
    }
}
