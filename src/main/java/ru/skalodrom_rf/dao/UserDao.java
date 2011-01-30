package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.skalodrom_rf.model.User;

import java.util.List;


@AutoDAO
public interface UserDao extends Dao<User, String> {
    @Finder(query="from User where login = :login")
    public List<User> findByLogin(@Named("login") String login);

}
