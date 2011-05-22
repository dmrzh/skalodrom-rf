package ru.skalodrom_rf.dataloader;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ClimbTimeDao;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.dao.SkalodromDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * Load initial data to database/
 */
public class Dataloader {
    private static final Logger LOG= LoggerFactory.getLogger(Dataloader.class);
    @Resource
    UserDao userDao;

    @Resource
    SkalodromDao skalodromDao;


    @Resource
    PrefferedWeekDayDao prefferedWeekDayDao;

    @Resource
    ClimbTimeDao climbTimeDao;

    @Transactional
    public  void initializeDatabase(){
        LOG.debug("start database initialization");
        prefferedWeekDayDao.create(new WeekDay(1L,"monday"));
        final WeekDay tuesday = new WeekDay(2L, "tuesday");
        prefferedWeekDayDao.create(tuesday);
        prefferedWeekDayDao.create(new WeekDay(3L,"wednesday"));
        prefferedWeekDayDao.create(new WeekDay(4L,"thursday"));
        prefferedWeekDayDao.create(new WeekDay(5L,"friday"));
        prefferedWeekDayDao.create(new WeekDay(6L,"saturday"));
        prefferedWeekDayDao.create(new WeekDay(7L,"sunday"));
        final ClimbTime climbTime = new ClimbTime();
        climbTime.setDate(new LocalDate());
        climbTime.setTime(Time.DAY);
        climbTimeDao.create(climbTime);


        final Skalodrom dds = new Skalodrom("ДДС");
        skalodromDao.create(dds);
        skalodromDao.create(new Skalodrom("Скаласити"));
        skalodromDao.create(new Skalodrom("Экстрим"));
        skalodromDao.create(new Skalodrom("Южная"));
        skalodromDao.create(new Skalodrom("Бауманская"));
        skalodromDao.create(new Skalodrom("Скалатория"));

         Random rnd=new Random();
         List<WeekDay> allWeeksDays = prefferedWeekDayDao.findAll();
          List<Skalodrom> allSkal= skalodromDao.findAll();
         User user;
        for(int i=0;i<0;i++){
             user = new User();
            user.setLogin("dima"+i);
            user.getProfile().setFio("Дима "+i);

            user.setPassword("");
            user.getProfile().setEmail("dima@rzhevskiy.info");
            //user.getProfile().getWhenClimb().add(climbTime);

            user.getProfile().getPrefferedWeekDay().add(allWeeksDays.get(rnd.nextInt(7)));
            userDao.create(user);

            user.getProfile().getWhereClimb().add(allSkal.get(rnd.nextInt(6)));
           // dds.getWhoClimb().add(user.getProfile());
             userDao.saveOrUpdate(user);
        }

        skalodromDao.saveOrUpdate(dds);



        LOG.debug("database initialized");


    }
}
