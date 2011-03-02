package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import net.sf.autodao.Finder;
import ru.skalodrom_rf.model.Scalodrom;

import java.util.List;

/**
 */
@AutoDAO
public interface ScalodromDao extends Dao<Scalodrom, Long> {
    @Finder(query = "from Scalodrom")
    List<Scalodrom> findAll();     
}
