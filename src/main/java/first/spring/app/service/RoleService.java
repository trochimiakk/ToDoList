package first.spring.app.service;

import first.spring.app.dao.RoleDao;
import first.spring.app.models.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleDao roleDao;

    @Transactional
    public RoleModel getBasicRole(){
        return roleDao.getBasicRole();
    }

}
