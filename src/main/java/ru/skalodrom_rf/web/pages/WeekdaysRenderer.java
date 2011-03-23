package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import ru.skalodrom_rf.model.PrefferedWeekDay;

/**.*/
class WeekdaysRenderer implements IChoiceRenderer<PrefferedWeekDay> {
    WeekdaysRenderer() {
    }


    @Override
    public Object getDisplayValue(PrefferedWeekDay object) {
        final PrefferedWeekDay weekDay = (PrefferedWeekDay) object;
        final String name = weekDay.getName();
        final ClassStringResourceLoader psrl = new ClassStringResourceLoader(WeekdaysRenderer.class);
        return psrl.loadStringResource(null,"weekdays."+name);
    }

    @Override
    public String getIdValue(PrefferedWeekDay object, int index) {
        return ((PrefferedWeekDay)object).getId().toString();
    }
}
