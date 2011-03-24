package ru.skalodrom_rf.web.hibernate;

import net.sf.autodao.Dao;
import net.sf.autodao.PersistentEntity;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.DaoFinder;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**.*/
public class HibernateQueryDataProvider <E extends PersistentEntity<EK>,
                                    EK extends Serializable>
                                                                        implements IDataProvider<E> {
    @SpringBean
    DaoFinder daoFinder;

    Class<Dao<E,EK>> daoClass;
    String method;
    IModel[] params;

    public HibernateQueryDataProvider(Class<Dao<E,EK>> daoClass, String method, IModel... params) {
        this.daoClass=daoClass;
        this.method=method;
        this.params=params;
        InjectorHolder.getInjector().inject(this);
    }

    @Override
    public void detach() {
        for(IModel m:params){
            m.detach();
        }

    }

    Collection<E> getFullList(){
        Class[] paramTypes = new Class[params.length];
        Object[] paramValues = new Object[params.length];
        for(int i=0;i<params.length;i++){
            paramValues[i]=params[i].getObject();
            paramTypes[i]=params[i].getObject().getClass();
        }

        final Dao dao = daoFinder.findDaoByClass(daoClass);
        final Collection<E> o;
        try {
            final Method method1 = daoClass.getMethod(method, paramTypes);
            o = (Collection<E>)method1.invoke(dao, paramValues);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    @Override
    public Iterator<? extends E> iterator(int first, int count) {
        return new ArrayList<E>(getFullList()).subList(first,first+count).iterator();
    }

    @Override
    public int size() {
        return getFullList().size();
    }

    @Override
    public IModel<E> model(E object) {
        return new HibernateModel<E,EK>(object);
    }
}
