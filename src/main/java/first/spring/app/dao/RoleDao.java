package first.spring.app.dao;

import first.spring.app.models.RoleModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao {

    private final String BASIC_USER_ROLE = "USER";

    @Autowired
    SessionFactory sessionFactory;

    public RoleModel getBasicRole(){
        return sessionFactory.getCurrentSession().get(RoleModel.class, BASIC_USER_ROLE);
    }

}
