package ru.skalodrom_rf;

import org.apache.wicket.RestartResponseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;
import ru.skalodrom_rf.security.HibernateUserDetailsService;
import ru.skalodrom_rf.web.pages.LoginPage;

import javax.annotation.Resource;

/**.*/
public class UserService {
    @Resource
    UserDao userDao;

    public User getCurrentUser(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDao.get(authentication.getName());
    }
    public boolean isUserAnonymous(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return ! authentication.getAuthorities().contains(HibernateUserDetailsService.USER_ROLE);
    }
}
