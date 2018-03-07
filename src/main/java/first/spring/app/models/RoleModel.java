package first.spring.app.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleModel {

    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserModel> users;

    public RoleModel() { }

    public RoleModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
