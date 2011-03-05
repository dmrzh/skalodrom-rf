package ru.skalodrom_rf.web;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

/**.*/
public class HibernateFieldModel<E extends PersistentEntity<EK>,
                                    EK extends Serializable,
                                    F extends PersistentEntity<FK>,
                                    FK extends Serializable >
                                                                        implements IModel<Collection<F>>{
    private static final Logger LOG= LoggerFactory.getLogger(HibernateFieldModel.class);
    private HibernateModel<E, EK> eHibernateModel;
    private String field;

    public HibernateFieldModel(E entity, String field) {
        this.eHibernateModel = new HibernateModel<E, EK>(entity);
        this.field = field;
    }

    @Override
    public void detach() {
        eHibernateModel.detach();
    }

    @Override
    public Collection<F> getObject() {
        return getField();
    }

    @Override
    public void setObject(Collection<F> object) {
        throw new RuntimeException("not implemented");
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


}
