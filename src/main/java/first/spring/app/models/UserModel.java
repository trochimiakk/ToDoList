package first.spring.app.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserModel {

    public UserModel(){
        this.enabled = true;
    }

    public UserModel(String username, String email, String password, String confirmPassword, boolean enabled, RoleModel role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.enabled = enabled;
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return enabled == userModel.enabled &&
                Objects.equals(username, userModel.username) &&
                Objects.equals(email, userModel.email) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(confirmPassword, userModel.confirmPassword) &&
                Objects.equals(role, userModel.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, email, password, confirmPassword, enabled, role);
    }
}
