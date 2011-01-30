package ru.skalodrom_rf.dataloader;

import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;

import javax.annotation.Resource;

/**
 * Load initial data to database/
 */
public class Dataloader {
    @Resource
    UserDao userDao;

    @Transactional
    public  void initializeDatabase(){
        final User user = new User();
        user.setLogin("dima");
        user.setPassword("");
        user.getProfile().setEmail("dima@rzhevskiy.info");
        userDao.create(user);


    }
}
