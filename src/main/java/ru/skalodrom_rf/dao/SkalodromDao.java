package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.Skalodrom;

import java.util.List;

/**
 */
@AutoDAO
public interface SkalodromDao extends Dao<Skalodrom, Long> {
    @Finder(query = "from Skalodrom order by name")
    List<Skalodrom> findAll();
}
