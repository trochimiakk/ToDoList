package first.spring.app.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    @Size(min = 3, max = 60, message = "Title length must be between 3 and 20")
    private  String title;

    @Column(name = "description", nullable = false)
    @Size(max = 1000, message = "Description length must be less than or equal to 1000")
    private String description;

    @Column(name = "done", nullable = false)
    private boolean done;

    @Column(name = "date", nullable = false)
    @Future(message = "Date must be a future date")
    @DateTimeFormat(pattern = "HH:mm dd-MM-yyyy")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private UserModel user;


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
}
