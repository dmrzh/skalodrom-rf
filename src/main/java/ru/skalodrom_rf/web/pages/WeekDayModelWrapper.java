package ru.skalodrom_rf.web.pages;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.model.WeekDay;

/**.*/
public class WeekDayModelWrapper implements IModel<WeekDay>{
    @SpringBean
    PrefferedWeekDayDao prefferedWeekDayDao;

    IModel<LocalDate> localDateModel;
    public WeekDayModelWrapper(IModel<LocalDate> localDateModel) {
        this.localDateModel=localDateModel;
         InjectorHolder.getInjector().inject(this);
    }

    @Override
    public void detach() {
        localDateModel.detach();
    }

    @Override
    public WeekDay getObject() {
        final LocalDate.Property property = localDateModel.getObject().dayOfWeek();
        return prefferedWeekDayDao.get(new Long(property.get()));
    }

    @Override
    public void setObject(WeekDay object) {
        throw new RuntimeException("not implemented");
    }
}
