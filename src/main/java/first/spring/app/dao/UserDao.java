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
        CriteriaQuery<UserModel> criteriaQuery = sessionFactory.getCriteriaBuilder().createQuery(UserModel.class);
        Root<UserModel> root = criteriaQuery.from(UserModel.class);
        criteriaQuery.select(root).where(sessionFactory.getCriteriaBuilder().equal(root.get("email"), email));
        try {
            return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex){
            return null;
        }

    }

    public UserModel findUserByUsername(String username){
        CriteriaQuery<UserModel> criteriaQuery = sessionFactory.getCriteriaBuilder().createQuery(UserModel.class);
        Root<UserModel> root = criteriaQuery.from(UserModel.class);
        criteriaQuery.select(root).where(sessionFactory.getCriteriaBuilder().equal(root.get("username"), username));
        try {
            return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException ex){
            return null;
        }

    }


    public void saveUser(UserModel user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sessionFactory.getCurrentSession().save(user);
    }

}
