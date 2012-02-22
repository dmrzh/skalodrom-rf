package ru.skalodrom_rf;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.dao.SkalodromDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**.*/

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:applicationContext.xml"})


public class ProfileTest {
    @Resource
    UserDao userDao;

    @Resource
    PrefferedWeekDayDao prefferedWeekDayDao;


    @Resource
    ProfileDao profileDao;
    @Resource
    SkalodromDao skalodromDao;

    @Test @Transactional
    public void testConstraint(){
        final Skalodrom skalodrom = skalodromDao.findAll().get(1);
        final List<Profile> profileList = profileDao.findByScalodromAndDate(skalodrom, new LocalDate(), Time.DAY);
        Assert.assertEquals(1,profileList.size());
    }

    /**SKAL-28 .*/
    @Test @Transactional
    public void testTwoWeekdays() throws Exception{
        final User user = new User();
        user.setLogin("nadya");
        user.getProfile().setFio("Надя");

        user.setPassword("");
        user.getProfile().getEmail().setNewAdress("nadya@rzhevskiy.info");
        userDao.create(user);
        List<WeekDay> all = prefferedWeekDayDao.findAll();

        Set<WeekDay> prefferedWeekDays = user.getProfile().getPrefferedWeekDay();
//        prefferedWeekDays.
        prefferedWeekDays.add(all.get(1));

        userDao.saveOrUpdate(user);
        prefferedWeekDays.add(all.get(2));
        userDao.saveOrUpdate(user);
        Assert.assertEquals(2,userDao.get("nadya").getProfile().getPrefferedWeekDay().size()) ;
        Thread.sleep(1000*60);
    }

}
