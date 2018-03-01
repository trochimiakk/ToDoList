package first.spring.app.service;

import first.spring.app.dao.UserDao;
import first.spring.app.exception.UserNotFoundException;
import first.spring.app.models.RoleModel;
import first.spring.app.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleService roleService;

    @Transactional
    public UserModel findUserByEmail(String email) throws UserNotFoundException {
        email = email.toLowerCase();
        return userDao.findUserByEmail(email);
    }

    @Transactional
    public UserModel findUserByUsername(String username) throws UserNotFoundException {
        return userDao.findUserByUsername(username);
    }

    @Transactional
    public void saveUser(UserModel user){
        RoleModel role = roleService.getBasicRole();
        user.setRole(role);
        userDao.saveUser(user);
    }

    @Transactional
    public boolean checkIfUserWithEmailAlreadyExists(String email){

        try {
            findUserByEmail(email);
        } catch (UserNotFoundException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean checkIfUserWithUsernameAlreadyExists(String username){

        try {
            findUserByUsername(username);
        } catch (UserNotFoundException e) {
            return false;
        }
        return true;
    }

}
