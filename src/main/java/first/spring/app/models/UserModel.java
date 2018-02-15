package first.spring.app.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel {

    public UserModel(){
        this.role = "USER";
        this.enabled = true;
    }

    @Id
    @Column(name = "username")
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @Email
    @Size(max = 50)
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 100, message = "Password length must be between 5 and 100")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
