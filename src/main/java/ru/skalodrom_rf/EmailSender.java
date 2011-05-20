package ru.skalodrom_rf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skalodrom_rf.model.User;

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

    public void sendMessage(User to, String subject, String text) {
        String email = to.getProfile().getEmail();
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
}
