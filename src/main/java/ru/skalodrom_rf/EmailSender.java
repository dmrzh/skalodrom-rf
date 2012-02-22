package ru.skalodrom_rf;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.EmailMessageDao;
import ru.skalodrom_rf.model.EmailMessage;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.User;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * .
 */
public class EmailSender {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);
    @Resource
    EmailMessageDao emailMessageDao;

    public void sendMessage(User to, String subject, String text) {
        String email = to.getProfile().getEmail().getAddress();
        sendMessage(email, subject, text);
    }

    public void sendMessage(String email, String subject, String text ) {
        try {
            final Properties props = new Properties();
            final InputStream stream = getClass().getClassLoader().getResourceAsStream("mail.properties");
            props.load(stream);

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    final String uname = (String) props.get("rf.skalodrom.mail.username");
                    final String upassword = (String) props.get("rf.skalodrom.mail.password");
                    return new PasswordAuthentication(uname, upassword);
                }
            };


            final Session mailSession = Session.getDefaultInstance(props, auth);

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("info@skalodrom-rf.ru"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject, "UTF-8");
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setHeader("Content-Transfer-Encoding", "quoted-printable");

            message.setText(text, "UTF-8");

            Transport.send(message);
            LOG.info("email message to " + email + " sended");
        } catch (Exception ex) {
            String msg = "email not sended to [" + email + "] with subject=[" + subject + "] with text=[" + text + "]";
            LOG.error(msg, ex);

        }
    }

    @Transactional
    public void  sendUserToUser(EmailMessage emailMessage){
        final String subjectText = "сообщение через сайт скалодром.рф[" + emailMessage.getTitle() + "]";

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String s = "\n\n Письмо отправлено с сайта скалодром.рф  от http://скалодром.рф/users/"+authentication.getName();
        s=s+"\n Чтобы ответить зайдите на http://скалодром.рф/users/"+authentication.getName()+" и выберите 'Написать письмо'";
        sendMessage(emailMessage.getTo(), subjectText, emailMessage.getMessage() + s);
        emailMessage.setSended(true);
        emailMessage.setRetry(emailMessage.getRetry()-1);
        emailMessageDao.saveOrUpdate(emailMessage);

    }
    private static String toProfileString(Profile whom) {
        return "["+whom.getUser().getLogin()+"]"+whom.getFio();
    }

    public void sendActivationMessage(User user, String title){
        final String prefixToWicketHandler = RequestCycle.get().getRequest().getRelativePathPrefixToWicketHandler();
        String str = "Добро пожаловать на скалором.рф \n код активации: " + user.getProfile().getEmail().getActivationCode()+
                "\n url активации: " + RequestUtils.toAbsolutePath(prefixToWicketHandler)+
                "activate.html?login="+user.getLogin()+ "&activationCode="+user.getProfile().getEmail().getActivationCode();

        str=str+"\n \n Чтобы вы были доступны в поиске, заполните свой профиль.";

        sendMessage(user.getProfile().getEmail().getNewAdress(), title, str);

    }
}
