package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.Profile;

import java.util.List;

/**
 */
@AutoDAO
public interface ProfileDao extends Dao<Profile, Long> {
     @Finder(query = "from Profile")
     List<Profile> findAll();   
}
