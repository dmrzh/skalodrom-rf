package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.ClimbTime;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.Scalodrom;
import ru.skalodrom_rf.model.Time;
import ru.skalodrom_rf.model.User;
import ru.skalodrom_rf.web.EnumRendererer;
import ru.skalodrom_rf.web.HibernateFieldDataProvider;
import ru.skalodrom_rf.web.HibernateModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**.*/
public class ProfileViewPage extends BasePage{
    @SpringBean
    UserDao userDao;
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
        add(new Label("fio", p.getFio()));
        add(new Label("email", p.getEmail()));
        add(new Label("phone", p.getPhone()));

        final HibernateModel<Profile,Long> model = new HibernateModel<Profile,Long>(p);
        final DynamicImageResource avatarImageResource = new AvatarImageResource(model);
        add(new Image("avatar", avatarImageResource));
        add(new Label("about", p.getAbout()));
        add(new Label("level", p.getClimbLevel().name()));
        add(new Label("weight", ""+p.getWeight()));

        final HibernateFieldDataProvider<Profile,Long,Scalodrom> provider = new HibernateFieldDataProvider<Profile,Long,Scalodrom>(p,"whereClimb");
        final DataView<Scalodrom> skalodromsView= new DataView<Scalodrom>("skalodromsView", provider){
            @Override
            protected void populateItem(Item<Scalodrom> scalodromItem) {
                final String skaladromName = scalodromItem.getModelObject().getName();
                scalodromItem.add(new Label("skaladromName", skaladromName));
            }
        };
        add(skalodromsView);
        

        final HibernateFieldDataProvider<Profile,Long,ClimbTime> whenProvider = new HibernateFieldDataProvider<Profile,Long,ClimbTime>(p,"whenClimb");
        final DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG,getRequest().getLocale());
        final DataView<ClimbTime> whenView= new DataView<ClimbTime>("whenView", whenProvider){
            @Override
            protected void populateItem(Item<ClimbTime> climbTimeItem) {
                final ClimbTime when = climbTimeItem.getModelObject();
                final EnumRendererer timeRendererer = new EnumRendererer(Time.class);
                final Date date = when.getDate().toDateTimeAtStartOfDay().toDate();
                climbTimeItem.add(new Label("date", dateFormat.format(date)));
                climbTimeItem.add(new Label("time", (String)timeRendererer.getDisplayValue(when.getTime())));
            }
        };
        add(whenView);

    }
}
