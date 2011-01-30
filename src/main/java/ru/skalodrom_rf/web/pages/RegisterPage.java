package ru.skalodrom_rf.web.pages;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.WebPage;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

public class RegisterPage extends WebPage{
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

            form.add(new RequiredTextField<String>("login"));
            form.add(new PasswordTextField("password"));
            form.add(new RequiredTextField<String>("profile.email"));
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
        try{
            final Properties props= new Properties();
            props.put("mail.smtp.host", "smtp-56.1gb.ru");
            props.put("mail.smtp.port", "465");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");

//            props.put("mail.from", "info@relc.1gb.ru");
            Authenticator auth = new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("u215104","5f50d771");
                }
            };
            

            final Session mailSession = Session.getDefaultInstance(props,auth);

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("info@skalodrom-rf.ru"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getProfile().getEmail()));
            message.setSubject("Регистрация");

            final String prefixToWicketHandler = RequestCycle.get().getRequest().getRelativePathPrefixToWicketHandler();
            String str = "Добро пожаловать на скалором.рф \n код активации: " + user.getActivationCode()+
                "\n url активации: " + RequestUtils.toAbsolutePath(prefixToWicketHandler)+
                "activate.html?login="+user.getLogin()+ "&activationCode="+user.getActivationCode();
            message.setText(str);

            Transport.send(message);
        }catch(Exception ex){
            ex.printStackTrace();

        }

    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }
}
