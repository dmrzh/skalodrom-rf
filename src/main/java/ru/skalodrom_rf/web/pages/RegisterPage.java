package ru.skalodrom_rf.web.pages;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skalodrom_rf.EmailSender;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RegisterPage extends BasePage{
    private static final Logger LOG=LoggerFactory.getLogger(RegisterPage.class);
    private CaptchaImageResource captchaImageResource;
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
    @SpringBean
    EmailSender emailSender;

    public RegisterPage() {
        captchaPassword=Integer.toString(prng.nextInt(1000));
        add(new FeedbackPanel("feedback"));
        final IModel<User> newUserModel = new LoadableDetachableModel<User>() {
            @Override
            protected User load() {
                return new User();
            }
        };
        final StatelessForm form = new StatelessForm("form", new CompoundPropertyModel(newUserModel));

            form.add(new RequiredTextField<String>("login").add(new StringValidator.LengthBetweenValidator(2,32)).add(new PatternValidator("[a-z0-9]*")));
            form.add(new PasswordTextField("password").add(new StringValidator.LengthBetweenValidator(5,32)));
            form.add(new RequiredTextField<String>("profile.email").add(EmailAddressValidator.getInstance()));
            captchaImageResource = new CaptchaImageResource(captchaPassword);

        final Image image = new Image("captchaImage", captchaImageResource);
        form.add(image);
            form.add(new RequiredTextField<String>("captchaText",new PropertyModel(this, "captchaText")));;


            form.add(new Button("submit"){
                @Override
                public void onSubmit() {
                        if(captchaPassword.equals(captchaText)){
                            User user=newUserModel.getObject();
                            if( user.getLogin()== null){
                                error("поле логин обязательное");
                            }else if(userDao.get(user.getLogin())!= null){
                                error("пользователь с таким логином уже существует");
                            } else{
                                user.setActivationCode(Math.abs(prng.nextInt()));
                                userDao.create(user);
                                //send activation email
                                sendActivationMessage(user);
                                captchaPassword=Integer.toString(prng.nextInt(1000));
                                captchaImageResource = new CaptchaImageResource(captchaPassword);
                                final Image image = new Image("captchaImage", captchaImageResource);
                                form.replace(image);
                                info("пользователь зарегистрирован. Письмо с интрукциями по активации аккаунта выслано на почту.");
                                LOG.debug("new user {} registred.", user.getLogin());
                            }
                        }else{
                            error("Проверочный код введён неправильно");
                        }
                }
            });
        add(form);
    }

    private void sendActivationMessage(User user){
        final String prefixToWicketHandler = RequestCycle.get().getRequest().getRelativePathPrefixToWicketHandler();
        String str = "Добро пожаловать на скалором.рф \n код активации: " + user.getActivationCode()+
            "\n url активации: " + RequestUtils.toAbsolutePath(prefixToWicketHandler)+
            "activate.html?login="+user.getLogin()+ "&activationCode="+user.getActivationCode();

        emailSender.sendMessage(user,"Регистрация",str);

    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }
}
