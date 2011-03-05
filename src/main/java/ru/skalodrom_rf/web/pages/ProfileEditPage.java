package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.dao.Utils;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.web.HibernateModel;

/**
 */
public class ProfileEditPage extends BasePage{
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private ProfileDao profileDao;

    public ProfileEditPage() {
        final Profile pe = Utils.getCurrntUser(userDao).getProfile();
        final HibernateModel<Profile,Long> model = new  HibernateModel<Profile,Long>(pe);
        final Form<Profile> form = new Form<Profile>("form", new CompoundPropertyModel(model)){
            @Override
            protected void onSubmit() {


                profileDao.saveOrUpdate(model.getObject());
                System.out.println("profile saved "+model.getObject());
            }
        };

        form.add(new TextField("fio"));
        form.add(new TextField("email"));
        form.add(new TextField("phone"));
        add(form);

    }
}
