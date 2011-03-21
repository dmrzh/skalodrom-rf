package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.Scalodrom;

import java.util.List;

/**
 */
@AutoDAO
public interface ProfileDao extends Dao<Profile, Long> {
     @Finder(query = "from Profile")
     List<Profile> findAll();
    @Finder(query = "select p from Profile p inner join p.whereClimb  as s1 where s1=:s")
//where p in :s.whoClimb and p.whenClimb.date=date                           s1 in p.whereClimb
     List<Profile> findByScalodromAndDate(@Named("s")Scalodrom s);
}
