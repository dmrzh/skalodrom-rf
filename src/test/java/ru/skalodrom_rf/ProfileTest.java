package ru.skalodrom_rf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.model.Scalodrom;

import javax.annotation.Resource;

/**.*/

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:applicationContext.xml"})


public class ProfileTest {

    @Resource
    ProfileDao profileDao;
    @Resource
    ScalodromDao scalodromDao;

    @Test @Transactional
    public void testConstraint(){
        final Scalodrom scalodrom = scalodromDao.findAll().get(1);
        profileDao.findByScalodromAndDate(scalodrom);
    }
}
