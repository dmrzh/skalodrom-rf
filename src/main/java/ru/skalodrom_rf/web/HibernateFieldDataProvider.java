package ru.skalodrom_rf.web;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**.*/
public class HibernateFieldDataProvider<E extends PersistentEntity<EK>,
                                    EK extends Serializable,
                                    F extends PersistentEntity<FK>,
                                    FK extends Serializable >
                                                                        implements IDataProvider<F>{
    private static final Logger LOG= LoggerFactory.getLogger(HibernateFieldDataProvider.class);
    private HibernateModel<E, EK> eHibernateModel;
    private String field;

    public HibernateFieldDataProvider(E entity,String field) {
        this.eHibernateModel = new HibernateModel<E, EK>(entity);
        eHibernateModel.detach();
        this.field=field;
    }

    @Override
    public void detach() {
        eHibernateModel.detach();
    }

    @Override
    public Iterator<? extends F> iterator(int first, int count) {
        return new ArrayList<F>(getField()).subList(first, first+count).iterator();
    }

    private Collection<F> getField() {
        final E object = eHibernateModel.getObject();
        try {
            final Method method = object.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
            return (Collection<F>)method.invoke(object);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return getField().size();
    }

    @Override
    public IModel<F> model(F object) {
        return new HibernateModel<F,FK>(object);
    }
}
