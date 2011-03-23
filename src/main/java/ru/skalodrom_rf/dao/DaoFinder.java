package ru.skalodrom_rf.dao;

import net.sf.autodao.Dao;
import net.sf.autodao.PersistentEntity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/**
 */
public class DaoFinder<T extends PersistentEntity<K>, K extends Serializable> implements ApplicationContextAware {
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;


    }

    public Dao<T, K> findDao(Class<T> tClass) {
        final Collection<Dao> daoCollection = applicationContext.getBeansOfType(Dao.class).values();
        for (Dao dao : daoCollection) {
            final Class[] cleanifaces = (Class[]) dao.getClass().getInterfaces();
            for (Class t : cleanifaces) {
                for (Type pt : t.getGenericInterfaces()){
                    if(! (pt instanceof ParameterizedType)) {
                        continue;
                    }
                    final Type[] actualTypeArguments = ((ParameterizedType) pt).getActualTypeArguments();
                    if (Arrays.asList(actualTypeArguments).contains(tClass)) {
                        return dao;
                    }
                }

            }
        }
        return null;
    }
    public Dao<T, K> findDaoByClass(Class<Dao<T, K>> tClass) {
        final Collection<Dao<T, K>> daoCollection = applicationContext.getBeansOfType(tClass).values();
        return daoCollection.iterator().next();
    }

}