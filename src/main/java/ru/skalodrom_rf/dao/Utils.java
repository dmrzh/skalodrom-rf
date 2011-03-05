package ru.skalodrom_rf.dao;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skalodrom_rf.model.User;

/**.*/
public class Utils {
    public static User getCurrntUser(UserDao userDao){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        final org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) principal;
        return userDao.get(u.getUsername());
    }
}
