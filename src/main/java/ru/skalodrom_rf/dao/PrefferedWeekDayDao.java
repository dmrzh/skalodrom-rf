package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.WeekDay;

import java.util.List;

/**.*/
@AutoDAO
public interface PrefferedWeekDayDao  extends Dao<WeekDay, Long> {
         @Finder(query = "from WeekDay")
         List<WeekDay> findAll();
}
