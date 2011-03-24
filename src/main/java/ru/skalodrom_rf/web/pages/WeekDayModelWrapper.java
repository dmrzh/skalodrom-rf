package ru.skalodrom_rf.web.pages;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.model.PrefferedWeekDay;

/**.*/
public class WeekDayModelWrapper implements IModel<PrefferedWeekDay>{
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
    public PrefferedWeekDay getObject() {
        final LocalDate.Property property = localDateModel.getObject().dayOfWeek();
        return prefferedWeekDayDao.get(new Long(property.get()));
    }

    @Override
    public void setObject(PrefferedWeekDay object) {
        throw new RuntimeException("not implemented");
    }
}
