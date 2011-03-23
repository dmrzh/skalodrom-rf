package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.PrefferedWeekDay;

import java.util.List;

/**.*/
@AutoDAO
public interface PrefferedWeekDayDao  extends Dao<PrefferedWeekDay, Long> {
         @Finder(query = "from PrefferedWeekDay")
         List<PrefferedWeekDay> findAll();
}
