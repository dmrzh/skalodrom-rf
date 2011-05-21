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
import ru.skalodrom_rf.EmailSender;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.User;


/**
 */
public class SendMessagePage extends BasePage{
    @SpringBean
    EmailSender emailSender;
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
            public void onSubmit() {
                final String subjectText = "сообщение через сайт скалодром.рф[" + subject.getObject() + "]";
                final User to = whomModel.getObject().getUser();
                 final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String s = "\n\n Письмо отправлено с сайта скалодром.рф  от http://скалодром.рф/users/"+authentication.getName();
                emailSender.sendMessage(to, subjectText,text.getObject()+ s);
                RequestCycle.get().setResponsePage(IndexPage.class);
            }
        };
        form.add(button);
    }

    private static String toProfileString(Profile whom) {
        return "["+whom.getUser().getLogin()+"]"+whom.getFio();
    }
}
