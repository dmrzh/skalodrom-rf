package ru.skalodrom_rf.web.pages;

import net.sf.wicketautodao.model.HibernateModel;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.EmailSender;
import ru.skalodrom_rf.UserService;
import ru.skalodrom_rf.dao.EmailMessageDao;
import ru.skalodrom_rf.model.EmailMessage;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.User;
import ru.skalodrom_rf.web.AuthenticatedUser;


/**
 */
@AuthenticatedUser
public class SendMessagePage extends BasePage{
    @SpringBean
    EmailSender emailSender;
    
    @SpringBean
    UserService userService;
    
    @SpringBean
    EmailMessageDao emailMessageDao;

    public SendMessagePage(final HibernateModel<Profile> whomModel) {
        add(new Label("whom", toProfileString(whomModel.getObject())));
        final Form form = new Form("form");
        add(form);
        final Model<String> subject = new Model<String>();
        form.add(new RequiredTextField<String>("subject", subject));
        final Model<String> text = new Model<String>();
        form.add(new TextArea<String>("text", text));
        final Button button = new Button("submit") {
            @Override
            @Transactional()
            public void onSubmit() {

                EmailMessage emailMessage = new EmailMessage(userService.getCurrentUser(), whomModel.getObject().getUser(),
                        subject.getObject(), text.getObject());

                saveMessage(emailMessage);
                emailSender.sendUserToUser(emailMessage);
                RequestCycle.get().setResponsePage(IndexPage.class);
            }

            @Transactional(propagation = Propagation.REQUIRES_NEW)
            public void saveMessage(EmailMessage emailMessage) {
                emailMessageDao.saveOrUpdate(emailMessage);
            }
        };
        form.add(button);
    }

    private static String toProfileString(Profile whom) {
        return "["+whom.getUser().getLogin()+"]"+whom.getFio();
    }
}
