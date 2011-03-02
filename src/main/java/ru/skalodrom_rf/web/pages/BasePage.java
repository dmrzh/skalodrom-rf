package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skalodrom_rf.web.components.LoginPanel;
import ru.skalodrom_rf.web.components.LogoutPanel;

/**.*/
public class BasePage extends WebPage{
    public BasePage() {
        init();        
    }

    public BasePage(PageParameters parameters) {
        init();
    }

    private void init() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();
        if("ROLE_ANONYMOUS".equals(grantedAuthority.getAuthority())){
            add(new LoginPanel("loginPanel"));
        }else{
            add(new LogoutPanel("loginPanel"));
        }
    }
}
