package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import net.sf.autodao.Named;
import org.joda.time.LocalDate;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.Skalodrom;
import ru.skalodrom_rf.model.Time;
import ru.skalodrom_rf.model.WeekDay;

import java.util.List;

/**
 */
@AutoDAO
public interface ProfileDao extends Dao<Profile, Long> {
     @Finder(query = "from Profile")
     List<Profile> findAll();
    @Finder(query = "select p " +
            "from Profile p " +
            "inner join p.whereClimb  as s1 " +
            "inner join p.whenClimb as climbTime  " +
            "where s1=:s and climbTime.date=:d and climbTime.time=:t")
     List<Profile> findByScalodromAndDate(@Named("s")Skalodrom s, @Named("d")LocalDate date, @Named("t")Time time);

     @Finder(query = "select p " +
             "from Profile p " +
             "inner join p.whereClimb  as s1 " +
             "inner join p.prefferedWeekDay as weekDay  " +
             "where s1=:s and weekDay=:wd")
     List<Profile> findByScalodromAndWeekDay(@Named("s")Skalodrom s, @Named("wd") WeekDay prefferedWeekDay);

     @Finder(query = "select p from Profile p where p.email=:email")
     List<Profile> findByEmail(@Named("email")String email);

}
