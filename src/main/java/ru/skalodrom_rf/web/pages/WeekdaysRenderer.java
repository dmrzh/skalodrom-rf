package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import ru.skalodrom_rf.model.WeekDay;

/**.*/
class WeekdaysRenderer implements IChoiceRenderer<WeekDay> {
    WeekdaysRenderer() {
    }


    @Override
    public Object getDisplayValue(WeekDay weekDay ) {
        final String name = weekDay.getName();
        final ClassStringResourceLoader psrl = new ClassStringResourceLoader(WeekdaysRenderer.class);
        return psrl.loadStringResource(null,"weekdays."+name);
    }

    @Override
    public String getIdValue(WeekDay object, int index) {
        return object.getId().toString();
    }
}
