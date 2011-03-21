package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.model.ClimbLevel;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.Scalodrom;
import ru.skalodrom_rf.model.Time;
import ru.skalodrom_rf.web.EnumRendererer;
import ru.skalodrom_rf.web.HibernateModel;
import ru.skalodrom_rf.web.HibernateModelList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 */
public class IndexPage extends BasePage{
    @SpringBean
    ProfileDao profileDao;
    @SpringBean
    private ScalodromDao scalodromDao;

    private final HibernateModel<Scalodrom,Long> skalModel = new HibernateModel<Scalodrom,Long>();

    private final Model<Date> dateModel = new Model<Date>(new Date());
    WebMarkupContainer resultContainer=new WebMarkupContainer("wrapper");
    public IndexPage() {
        this(new PageParameters());
    }

    public IndexPage(PageParameters parameters) {
        super(parameters);

        final List<Scalodrom> list = scalodromDao.findAll();

        skalModel.setObject(list.get(0));
        final Form form = new Form("form");

        add(form);

        ChoiceRenderer<Scalodrom> choiceRenderer = new ChoiceRenderer<Scalodrom>("name", "name");

        final HibernateModelList<Scalodrom, Long> modelList = new HibernateModelList<Scalodrom, Long>(list);
        final DropDownChoice dropDownChoice = new DropDownChoice<Scalodrom>("scalodrom", modelList, choiceRenderer);

        dropDownChoice.setModel(skalModel);
        form.add(dropDownChoice);

        DateTextField dateTextField = new DateTextField("date",dateModel, new StyleDateConverter("S-", true));
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
        dateTextField.add(datePicker);
        form.add(dateTextField);

        final Model<Time> timeModel = new Model<Time>(Time.EVENING);
        final EnumRendererer<Time> timeRenderer = new EnumRendererer<Time>(Time.class);
        form.add(new  DropDownChoice<Time>("time",timeModel, Arrays.asList(Time.values()), timeRenderer));

        form.add(new AjaxButton("submit"){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                System.out.println("skalModel = " + skalModel.getObject().getName());
                System.out.println("dateModel = " + dateModel.getObject());
                System.out.println("timeModel = " + timeModel.getObject());

                target.addComponent(resultContainer);
            }
        });

        resultContainer.setOutputMarkupId(true);
        resultContainer.add(createResults());
        add(resultContainer);


    }

    private DataView createResults() {
        List<HibernateModel<Profile,Long>> hibernateModels= new ArrayList<HibernateModel<Profile,Long>>();
        for(Profile pe:skalModel.getObject().getWhoClimb()){
            hibernateModels.add(new HibernateModel<Profile,Long>(pe));
        }

        final ListDataProvider<HibernateModel<Profile,Long>> dataProvider = new ListDataProvider(hibernateModels);
        final DataView profilesTable = new DataView<HibernateModel<Profile,Long>>("profilesTable",dataProvider ){
            @Override
            protected void populateItem(final Item<HibernateModel<Profile, Long>> hibernateModelItem) {
                final HibernateModel<Profile, Long> model = hibernateModelItem.getModelObject();
                final Profile profile = model.getObject();
                hibernateModelItem.add(new Label("fio",profile.getFio()));
                hibernateModelItem.add(new Label("weight",profile.getWeight()==null?"":""+profile.getWeight()));
                final EnumRendererer climbLevelRenderer = new EnumRendererer(ClimbLevel.class);
                hibernateModelItem.add(new Label("level", climbLevelRenderer.getDisplayValue(profile.getClimbLevel())));

                final PageParameters pageParameters = new PageParameters("0="+profile.getUser().getLogin());
                hibernateModelItem.add(new BookmarkablePageLink("viewProfile", ProfileViewPage.class, pageParameters));

                hibernateModelItem.add(new Link("sendMessage"){
                    @Override
                    public void onClick() {
                        RequestCycle.get().setResponsePage(new SendMessagePage(model));
                    }
                });
            }

        };
        return profilesTable;
    }
}
