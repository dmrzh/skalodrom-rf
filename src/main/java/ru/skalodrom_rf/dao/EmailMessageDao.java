package ru.skalodrom_rf.dao;

import net.sf.autodao.AutoDAO;
import net.sf.autodao.Dao;
import ru.skalodrom_rf.model.EmailMessage;

/**.*/
@AutoDAO
public interface EmailMessageDao extends Dao<EmailMessage,Long> {

}
