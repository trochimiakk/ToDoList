package first.spring.app.service;

import first.spring.app.dao.UserDao;
import first.spring.app.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Transactional
    public UserModel findUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }

    @Transactional
    public UserModel findUserByUsername(String username){
        return userDao.findUserByUsername(username);
    }

    @Transactional
    public void saveUser(UserModel user){
        userDao.saveUser(user);
    }

}
