package ru.skalodrom_rf.web.pages;

import net.sf.wicketautodao.model.HibernateFieldDataProvider;
import net.sf.wicketautodao.model.HibernateModel;
import net.sf.wicketautodao.model.HibernateModelList;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.PrefferedWeekDayDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.*;
import ru.skalodrom_rf.web.EnumRendererer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**.*/
public class ProfileViewPage extends BasePage{
    @SpringBean
    UserDao userDao;

    @SpringBean
    private PrefferedWeekDayDao prefferedWeekDayDao;


    public ProfileViewPage(PageParameters parameters) {
        super(parameters);
        String login = (String)parameters.get("0");
        if(login==null){
            RequestCycle.get().setResponsePage(FileNotFoundPage.class);
            return;
        }
        final User user = userDao.get(login);
        if(user==null){
            RequestCycle.get().setResponsePage(FileNotFoundPage.class);
            return;
        }
        final Profile p = user.getProfile();

        add(new Label("login", p.getUser().getLogin()));
        add(new Label("fio", p.getFio()));
        add(new Label("email", p.getEmail()));
        add(new Label("phone", p.getPhone()));

        final HibernateModel<Profile> model = new HibernateModel<Profile>(p);
        final DynamicImageResource avatarImageResource = new AvatarImageResource(model);
        add(new Image("avatar", avatarImageResource));
        add(new Label("about", p.getAbout()));
         final EnumRendererer<Sex> sexRenderer = new EnumRendererer<Sex>(Sex.class);
        add(new Label("sex",sexRenderer.getDisplayValue(p.getSex())));
        final EnumRendererer<ClimbLevel> climbLevelRenderer = new EnumRendererer<ClimbLevel>(ClimbLevel.class);

        add(new Label("level", climbLevelRenderer.getDisplayValue(p.getClimbLevel())));
        add(new Label("weight", p.getWeight()==null?"":""+p.getWeight()));

        final HibernateFieldDataProvider<Profile,Skalodrom> provider = new HibernateFieldDataProvider<Profile,Skalodrom>(p,"whereClimb");
        final DataView<Skalodrom> skalodromsView= new DataView<Skalodrom>("skalodromsView", provider){
            @Override
            protected void populateItem(Item<Skalodrom> scalodromItem) {
                final String skaladromName = scalodromItem.getModelObject().getName();
                scalodromItem.add(new Label("skaladromName", skaladromName));
            }
        };
        add(skalodromsView);

        final HibernateModelList<WeekDay> prefWeekDaysModel = new HibernateModelList<WeekDay>(p.getPrefferedWeekDay());
        add(new ListView<WeekDay>("prefferedWeekDay",prefWeekDaysModel){
            WeekdaysRenderer rdr=new WeekdaysRenderer();
            @Override
            protected void populateItem(ListItem<WeekDay> listItem) {
                final WeekDay weekDay = listItem.getModelObject();
                final String value = (String)rdr.getDisplayValue(weekDay);
                listItem.add(new Label("day", value));
            }
        });

        final HibernateFieldDataProvider<Profile,ClimbTime> whenProvider = new HibernateFieldDataProvider<Profile,ClimbTime>(p,"whenClimb");
        final DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG,getRequest().getLocale());
        final DataView<ClimbTime> whenView= new DataView<ClimbTime>("whenView", whenProvider){
            @Override
            protected void populateItem(Item<ClimbTime> climbTimeItem) {
                final ClimbTime when = climbTimeItem.getModelObject();
                final EnumRendererer timeRendererer = new EnumRendererer<Time>(Time.class);
                final Date date = when.getDate().toDateTimeAtStartOfDay().toDate();
                climbTimeItem.add(new Label("date", dateFormat.format(date)));
                climbTimeItem.add(new Label("time", timeRendererer.getDisplayValue(when.getTime())));
            }
        };
        add(whenView);
        add(new Link("sendMessage"){
                    @Override
                    public void onClick() {
                        RequestCycle.get().setResponsePage(new SendMessagePage(model));
                    }
           });


    }
}
