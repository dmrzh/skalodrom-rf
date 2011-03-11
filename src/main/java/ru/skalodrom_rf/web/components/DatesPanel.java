package ru.skalodrom_rf.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;
import ru.skalodrom_rf.dao.ClimbTimeDao;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.model.ClimbTime;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.web.HibernateFieldDataProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**.*/
public class DatesPanel extends Panel {
    @SpringBean
    ProfileDao profileDao;
    @SpringBean
    ClimbTimeDao climbTimeDao;


    private static DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG);
    public DatesPanel(String id, final HibernateFieldDataProvider<Profile,Long,ClimbTime> dp) {
        super(id);
        setOutputMarkupId(true);
        final DataView<ClimbTime> dataView = new DataView<ClimbTime>("dataView", dp){
            @Override
            protected void populateItem(final Item<ClimbTime> dateItem) {
                ClimbTime time = dateItem.getModelObject();
                final IModel<ClimbTime> timeModel = dateItem.getModel();
                dateItem.add(new Label("dateLabel", dateFormat.format(time.getDate().toDateTimeAtStartOfDay().toDate())));
                dateItem.add(new AjaxLink("removeLink"){
                    IModel<ClimbTime> when=timeModel ;
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        final Profile object = dp.getRootModel().getObject();
                        object.getWhenClimb().remove(when.getObject());
                        profileDao.saveOrUpdate(object);
                        climbTimeDao.delete(when.getObject());
                        target.addComponent(DatesPanel.this);
                    }

                });

            }
        };

        add(dataView);


        final Model<Date> dateModel = new Model<Date>(new Date());
        DateTextField dateTextField = new DateTextField("dateText",dateModel, new StyleDateConverter("S-", true));
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        dateTextField.add(datePicker);
        add(dateTextField);

        add(new AjaxButton("submit"){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                final Date date = dateModel.getObject();
                final Profile object = dp.getRootModel().getObject();
                final ClimbTime climbTime = new ClimbTime();
                climbTime.setDate(new LocalDate(date));
                climbTimeDao.create(climbTime);
                object.getWhenClimb().add(climbTime);
                profileDao.saveOrUpdate(object);
                target.addComponent(DatesPanel.this);
            }
        });

    }
}
