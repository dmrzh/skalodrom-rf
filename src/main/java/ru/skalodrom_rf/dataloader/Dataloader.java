package ru.skalodrom_rf.dataloader;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ClimbTimeDao;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.*;

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


    @Resource
    PrefferedWeekDayDao prefferedWeekDayDao;

    @Resource
    ClimbTimeDao climbTimeDao;

    @Transactional
    public  void initializeDatabase(){
        LOG.debug("start database initialization");
        prefferedWeekDayDao.create(new PrefferedWeekDay(1L,"monday"));
        final PrefferedWeekDay tuesday = new PrefferedWeekDay(2L, "tuesday");
        prefferedWeekDayDao.create(tuesday);
        prefferedWeekDayDao.create(new PrefferedWeekDay(3L,"wednesday"));
        prefferedWeekDayDao.create(new PrefferedWeekDay(4L,"thursday"));
        prefferedWeekDayDao.create(new PrefferedWeekDay(5L,"friday"));
        prefferedWeekDayDao.create(new PrefferedWeekDay(6L,"saturday"));
        prefferedWeekDayDao.create(new PrefferedWeekDay(7L,"sunday"));
        final ClimbTime climbTime = new ClimbTime();
        climbTime.setDate(new LocalDate());
        climbTime.setTime(Time.DAY);
        climbTimeDao.create(climbTime);



        final User user = new User();
        user.setLogin("http://rzhevskiy.info/journal/dima/");
        user.getProfile().setFio("Дима");

        user.setPassword("");
        user.getProfile().setEmail("dima@rzhevskiy.info");
        user.getProfile().getWhenClimb().add(climbTime);
        user.getProfile().getPrefferedWeekDay().add(tuesday);
        userDao.create(user);


        final Scalodrom dds = new Scalodrom("ДДС");
        scalodromDao.create(dds);

        user.getProfile().getWhereClimb().add(dds);
        dds.getWhoClimb().add(user.getProfile());
        
        userDao.saveOrUpdate(user);
        scalodromDao.saveOrUpdate(dds);

        scalodromDao.create(new Scalodrom("Скаласити"));
        scalodromDao.create(new Scalodrom("Экстрим"));
        scalodromDao.create(new Scalodrom("Южная"));
        scalodromDao.create(new Scalodrom("Бауманская"));
        scalodromDao.create(new Scalodrom("Скалатория"));

        LOG.debug("database initialized");


    }
}
