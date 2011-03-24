package ru.skalodrom_rf.web.pages;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.EmailSender;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.model.Profile;

import java.util.List;

/**
 */
public class ReminderPage extends BasePage{
     @SpringBean
     EmailSender emailSender;
     @SpringBean
     ProfileDao profileDao;

    public ReminderPage() {
        final Model emailModel = new Model();
        final Form form = new Form("form"){
            @Override
            protected void onSubmit() {
                final String email= (String)emailModel.getObject();
                final List<Profile> list = profileDao.findByEmail(email);
                StringBuilder sb=new StringBuilder("напоминание логина/пароля \n");
                if( ! list.isEmpty()){
                    for(Profile p:list){
                        sb.append("логин: ");
                        sb.append(p.getUser().getLogin());
                        sb.append("  пароль:");
                        sb.append(p.getUser().getPassword());
                        sb.append("\n");

                    }
                    emailSender.sendMessage(list.get(0).getUser(),"Напоминание пароля",sb.toString());
                }               
                RequestCycle.get().setResponsePage(IndexPage.class);
            }
        } ;

        add(form);
        form.add(new RequiredTextField<String>("email",emailModel));
        form.add(new Button("submit"));

    }
}
