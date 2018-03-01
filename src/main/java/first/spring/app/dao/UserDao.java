package first.spring.app.dao;

import first.spring.app.exception.UserNotFoundException;
import first.spring.app.models.UserModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserModel findUserByEmail(String email) throws UserNotFoundException {
        try{
            return sessionFactory.getCurrentSession().createQuery("from UserModel u where lower(u.email) =:email", UserModel.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException ex){
            throw new UserNotFoundException("User with email: " + email + " does not exist.");
        }

    }

    public UserModel findUserByUsername(String username) throws UserNotFoundException {
            UserModel user =  sessionFactory.getCurrentSession().get(UserModel.class, username);
            if (user != null){
                return user;
            }
            throw new UserNotFoundException("User with username: " + username + " does not exist.");
    }


    public void saveUser(UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }

}
