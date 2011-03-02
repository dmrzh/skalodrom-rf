package ru.skalodrom_rf.dao;

import net.sf.autodao.Dao;
import net.sf.autodao.PersistentEntity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.util.Collection;

/**
 */
public class DaoFinder<T extends PersistentEntity<K>, K extends Serializable>  implements ApplicationContextAware {
      ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;


    }
    public Dao<T,K> findDao(Class<T> tClass){
        final Collection<Dao> daoCollection = applicationContext.getBeansOfType(Dao.class).values();
        for(Dao dao:daoCollection){
            if(dao.getClass().getGenericInterfaces()[0] == tClass){
                return dao;
            }
        }
        return null;

    }
}
