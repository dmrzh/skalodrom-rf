package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.TUser;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RegisterPage extends WebPage{
    @SpringBean
    UserDao userDao;
    public RegisterPage() {
        final TUser user = new TUser();
        final StatelessForm form = new StatelessForm("form", new CompoundPropertyModel(user));
            form.add(new TextField("login"));
            form.add(new TextField("profile.email"));
            form.add(new Button("submit"){
                @Override
                public void onSubmit() {
                    try {
                        SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
                        user.setActivationCode(""+prng.nextInt());
                        userDao.create(user);

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                }
            });
        add(form);
    }
}
