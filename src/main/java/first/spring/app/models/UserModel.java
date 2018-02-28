package first.spring.app.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel {

    public UserModel(){
        this.enabled = true;
    }

    @Id
    @Column(name = "username")
    @Length(min = 3, max = 20, message = "{user.username.length}")
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "{user.email.invalid}")
    @Length(max = 50, message = "{user.email.length}")
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 5, max = 100, message = "{user.password.length}")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private RoleModel role;


    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
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
