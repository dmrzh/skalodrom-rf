package ru.skalodrom_rf.web;

import net.sf.autodao.Dao;
import net.sf.autodao.PersistentEntity;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skalodrom_rf.dao.DaoFinder;

import java.io.Serializable;

/**.*/
public class HibernateModel<T extends PersistentEntity<K>, K extends Serializable>
        extends LoadableDetachableModel<T>  {
    private static final Logger LOG= LoggerFactory.getLogger(HibernateModel.class);
    @SpringBean
    DaoFinder daoFinder;
    K id;
    Class<T> tClass;

    public HibernateModel() {
        init();
    }


    public HibernateModel(T pe) {
        super(pe);
        init();
    }

    private void init() {
        InjectorHolder.getInjector().inject(this);
    }

    @Override
    protected void onDetach() {
        T pe = getObject();
        id=pe.getPrimaryKey();
        tClass=(Class<T>)pe.getClass();
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
