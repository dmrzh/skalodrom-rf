package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.model.User;
import ru.skalodrom_rf.security.HibernateUserDetailsService;
import ru.skalodrom_rf.web.components.AfterActivationPanel;
import ru.skalodrom_rf.web.components.EmptyPanel;

/**
 */
public class ActivateUserPage extends BasePage {

    private static final Logger LOG= LoggerFactory.getLogger(ActivateUserPage.class);

    @SpringBean
    UserDao userDao;


    public ActivateUserPage(PageParameters parameters) {
        super(parameters);
        try {
            String login = extractParam(parameters, "login");
            final User user = userDao.get(login);
            if (user == null) {
                throwExeption();
            }

            String activationCode = extractParam(parameters, "activationCode");
            if (user.getActivationCode()!=null &&!activationCode.equals(user.getActivationCode().toString())) {
                throwExeption();
            }
            add(new Label("resultMessage", "Аккаунт активирован!"));
            user.setActivationCode(null);
            userDao.saveOrUpdate(user);
            autoLogin(user);
            add(new AfterActivationPanel("afterActivation"));

        } catch (IllegalArgumentException npe) {
            add(new Label("resultMessage", "Аккаунт НЕ активирован!"));
            add(new EmptyPanel("afterActivation"));
        }
    }

    private void throwExeption() {
        throw new IllegalArgumentException("incorrect activation code");
    }

    private String extractParam(PageParameters parameters, String s) {
        final Object o = parameters.get(s);
        if (!(o instanceof String[])) {
            throwExeption();
        }
        String[] sa = (String[]) o;
        if (sa.length != 1) {
            throwExeption();
        }
        if (sa[0] == null) {
            throwExeption();
        }
        if (sa[0].trim().length() == 0) {
            throwExeption();
        }
        return sa[0].trim();
    }

    /**
     * Automatic login after successful registration.
     *
     * @param user
     */
    public boolean autoLogin(User user) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword());

            // Place the new Authentication object in the security context.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            LOG.error("Exception", e);
            return false;
        }
        return true;
    }


}
