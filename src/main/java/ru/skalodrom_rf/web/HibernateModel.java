package ru.skalodrom_rf.web;

import net.sf.autodao.Dao;
import net.sf.autodao.PersistentEntity;
import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import ru.skalodrom_rf.dao.DaoFinder;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 */
@Configurable
public class HibernateModel<T extends PersistentEntity<K>, K extends Serializable> extends LoadableDetachableModel<T>  {
    private static final Logger LOG= LoggerFactory.getLogger(HibernateModel.class);
    @Resource
    DaoFinder daoFinder;
    K id;
    Class<T> tClass;

    public HibernateModel() {
    }

    public HibernateModel(T pe) {
        super(pe);
    }

    @Override
    protected void onDetach() {
        T pe = getObject();
        id=pe.getPrimaryKey();
        tClass=(Class<T>)pe.getClass();
    }

    @Override
    protected void onAttach() {
        super.onAttach();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected T load() {
        final Dao<T,K> dao = daoFinder.findDao(tClass);
        LOG.trace("dao={}",dao);
        return dao.get(id);
    }

    public DaoFinder getDaoFinder() {
        return daoFinder;
    }

    public void setDaoFinder(DaoFinder daoFinder) {
        this.daoFinder = daoFinder;
    }
}
