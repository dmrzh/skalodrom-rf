package ru.skalodrom_rf.web.pages;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.TUser;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RegisterPage extends WebPage{
    private final CaptchaImageResource captchaImageResource;
    private String captchaText;
    private String captchaPassword;
    static private SecureRandom prng ;
    {
        try {
           prng = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  
        }
    }

    @SpringBean
    UserDao userDao;
    public RegisterPage() {
        captchaPassword=Integer.toString(prng.nextInt(1000));
        add(new FeedbackPanel("feedback"));
        final IModel<TUser> newUserModel = new LoadableDetachableModel<TUser>() {
            @Override
            protected TUser load() {
                return new TUser();
            }
        };
        final StatelessForm form = new StatelessForm("form", new CompoundPropertyModel(newUserModel));

            form.add(new TextField<String>("login"));
            form.add(new PasswordTextField("password"));
            form.add(new TextField<String>("profile.email"));
            captchaImageResource = new CaptchaImageResource(captchaPassword);

            form.add(new Image("captchaImage", captchaImageResource));
            form.add(new RequiredTextField<String>("captchaText",new PropertyModel(this, "captchaText")));;


            form.add(new Button("submit"){
                @Override
                public void onSubmit() {
                        if(captchaPassword.equals(captchaText)){
                            TUser user=newUserModel.getObject();
                            user.setActivationCode(""+prng.nextInt());
                            userDao.create(user);
                            //send activation email
                        }else{
                            error("Проверочный код введён неправильно");
                        }


                }
            });
        add(form);
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }
}
