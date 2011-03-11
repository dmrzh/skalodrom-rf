package ru.skalodrom_rf;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ClimbTimeDao;
import ru.skalodrom_rf.model.ClimbTime;
import ru.skalodrom_rf.model.Time;

import javax.annotation.Resource;

/**.*/

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:applicationContext.xml"})


public class ClimbTimeTest {

    @Resource
    ClimbTimeDao climbTimeDao;
    @Test @Transactional
    public void testConstraint(){
        final ClimbTime ct = new ClimbTime();
        ct.setDate(new LocalDate());
        ct.setTime(Time.DAY);
        climbTimeDao.create(ct);


        

    }
}
