package ru.skalodrom_rf.web.hibernate;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
                                    F>
                                                                        implements IDataProvider<F>{
    private static final Logger LOG= LoggerFactory.getLogger(HibernateFieldDataProvider.class);
    private HibernateModel<E, EK> rootHibernateModel;
    private String field;

    public HibernateFieldDataProvider(E entity,String field) {
        this.rootHibernateModel = new HibernateModel<E, EK>(entity);
        rootHibernateModel.detach();
        this.field=field;
    }

    @Override
    public void detach() {
        rootHibernateModel.detach();
    }

    @Override
    public Iterator<? extends F> iterator(int first, int count) {
        return new ArrayList<F>(getField()).subList(first, first+count).iterator();
    }

    private Collection<F> getField() {
        final E object = rootHibernateModel.getObject();
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
        if(object instanceof PersistentEntity){
            return new HibernateModel((PersistentEntity)object);
        }else if (object instanceof Serializable){
             return new Model((Serializable)object);
        }else{
            throw new RuntimeException("object not PersistentEntity or not Serializable");
        }

    }
    public HibernateModel<E, EK> getRootModel(){
        return rootHibernateModel;

    }
}
