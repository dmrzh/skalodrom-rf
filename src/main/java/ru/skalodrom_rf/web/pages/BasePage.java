package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.web.components.ActivatePanel;
import ru.skalodrom_rf.web.components.LoginPanel;
import ru.skalodrom_rf.web.components.LogoutPanel;

import java.text.DateFormat;
import java.util.Calendar;

/**.*/
public class BasePage extends WebPage{

    @SpringBean
    private UserDao userDao;

    public BasePage() {
        init();        
    }

    public BasePage(PageParameters parameters) {
        init();
    }

    private void init() {
        add(new BookmarkablePageLink<Void>("indexLink", IndexPage.class)); //link to home page
        add(new Label("users",userDao.getUserCount().toString()));
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            final GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();//todo
            if("ROLE_ANONYMOUS".equals(grantedAuthority.getAuthority())){
                add(new LoginPanel("loginPanel"));
            }else if("ROLE_NOT_ACTIVATED_USER".equals(grantedAuthority.getAuthority())){
                 add(new ActivatePanel("loginPanel"));
            }else{ 
                add(new LogoutPanel("loginPanel"));
            }
        }else{
             add(new Label("loginPanel", "no spring security filters installed"));
        }
        addStatisticLabel();
    }

    private void addStatisticLabel() {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, getSession().getLocale());
        final Calendar cal = Calendar.getInstance(getLocale());
        //cal.add(Calendar.DAY_OF_WEEK,1);
        final String s = dateFormat.format(cal.getTime());
        add(new Label("nextDate", s));
    }
}
