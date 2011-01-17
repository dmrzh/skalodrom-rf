package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.skalodrom_rf.model.TUser;

import java.util.List;


@AutoDAO
public interface UserDao extends Dao<TUser, String> {
    @Finder(query="from TUser where login = :login")
    public List<TUser> findByLogin(@Named("login") String login);

}
