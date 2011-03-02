package ru.skalodrom_rf.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.Scalodrom;
import ru.skalodrom_rf.model.User;

import javax.annotation.Resource;

/**
 * Load initial data to database/
 */
public class Dataloader {
    private static final Logger LOG= LoggerFactory.getLogger(Dataloader.class);
    @Resource
    UserDao userDao;

    @Resource
    ScalodromDao scalodromDao;

    @Transactional
    public  void initializeDatabase(){
        LOG.debug("start database initialization");
        
        final User user = new User();
        user.setLogin("dima");
        user.setPassword("");
        user.getProfile().setEmail("dima@rzhevskiy.info");
        userDao.create(user);


        scalodromDao.create(new Scalodrom("ДДС"));
        scalodromDao.create(new Scalodrom("Скаласити"));
        scalodromDao.create(new Scalodrom("Экстрим"));
        scalodromDao.create(new Scalodrom("Южная"));
        scalodromDao.create(new Scalodrom("Бауманская"));
        scalodromDao.create(new Scalodrom("Скалатория"));

        LOG.debug("database initialized");


    }
}
