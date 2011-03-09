package ru.skalodrom_rf.web;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**.*/
public class EnumRendererer<E extends Enum> implements IChoiceRenderer<E>{
    private static final Logger LOG= LoggerFactory.getLogger(EnumRendererer.class);


    public EnumRendererer(Class<E> eClass) {
        this.ce=eClass;
        final ClassLoader loader = getClass().getClassLoader();
        final String s = eClass.getName().replace('.','/') + ".properties";
        final InputStream stream = loader.getResourceAsStream(s);
        try {
            i18n.load(stream);
        } catch (IOException ex) {
            LOG.error(ex.toString(),ex);
        }
    }
    private Class<E> ce;
    Properties i18n=new Properties();

    @Override
    public Object getDisplayValue(E object) {
        return i18n.get(object.name());
    }

    @Override
    public String getIdValue(E object, int index) {
        return object.name();
    }
}
