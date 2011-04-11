package ru.skalodrom_rf.web.hibernate;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;

/**.*/
public class HibernateFieldModel<E extends PersistentEntity, F extends PersistentEntity>
                                                                        implements IModel<Collection<F>>{
    private static final Logger LOG= LoggerFactory.getLogger(HibernateFieldModel.class);
    private HibernateModel<E> eHibernateModel;
    private String field;

    public HibernateFieldModel(E entity, String field) {
        this.eHibernateModel = new HibernateModel<E>(entity);
        this.field = field;
    }

    @Override
    public void detach() {
        eHibernateModel.detach();
    }

    @Override
    public Collection<F> getObject() {
        final E object = eHibernateModel.getObject();
        try {
            final Method method = object.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
            return (Collection<F>)method.invoke(object);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public void setObject(Collection<F> object) {
        throw new RuntimeException("not implemented");
    }


}
