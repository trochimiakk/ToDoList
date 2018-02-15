package first.spring.app.dao;

import first.spring.app.models.UserModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserModel findUserByEmail(String email){
        try{
            return sessionFactory.getCurrentSession().createQuery("from UserModel u where u.email=:email", UserModel.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException ex){
            return null;
        }

    }

    public UserModel findUserByUsername(String username){
            return sessionFactory.getCurrentSession().get(UserModel.class, username);
    }


    public void saveUser(UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }

}
