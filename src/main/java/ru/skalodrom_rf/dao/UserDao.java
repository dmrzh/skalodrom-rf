package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.User;


@AutoDAO
public interface UserDao extends Dao<User, String> {
    @Finder(query = "select count(*) from User")
    Long getUserCount();
}
