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

}
