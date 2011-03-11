package ru.skalodrom_rf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})

public class UserTest {
    @Resource
    UserDao userDao;

    @Test
    @Transactional()
    public void doSaveUserTest(){
        final User u = new User();
        u.setLogin("dima");
        u.setPassword("dima");
        u.setActivationCode(2134);
        userDao.create(u);
        Assert.assertEquals(userDao.get("dima").getActivationCode().intValue(),2134);
    }


}
