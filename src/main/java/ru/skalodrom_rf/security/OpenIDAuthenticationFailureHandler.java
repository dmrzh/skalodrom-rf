package ru.skalodrom_rf.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.UserDao;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handle egistration of new OpenId User/
 */
public class OpenIDAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
     private static final Logger LOG= LoggerFactory.getLogger(OpenIDAuthenticationFailureHandler.class);

    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isOpenIdRegistration(exception)) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            request.getSession(true).setAttribute("USER_OPENID_CREDENTIAL", exception.getExtraInformation());
            OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) exception.getAuthentication();
            registerWithOpenId(token.getIdentityUrl()) ;
            try {
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (Exception e) {
                SecurityContextHolder.getContext().setAuthentication(null);
                LOG.error("autologin error", e);
            }
            redirectStrategy.sendRedirect(request, response, "/profileEdit.html");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    private boolean isOpenIdRegistration(AuthenticationException exception) {
        if(! isNotFound(exception)) return false;
        if(! isOpenIdToken(exception)) return false;

        OpenIDAuthenticationToken authentication = (OpenIDAuthenticationToken) exception.getAuthentication();
        return  authentication.getStatus().equals(OpenIDAuthenticationStatus.SUCCESS);
    }

    private boolean isOpenIdToken(AuthenticationException exception) {
        return exception.getAuthentication() instanceof OpenIDAuthenticationToken;
    }

    private boolean isNotFound(AuthenticationException exception) {
       return exception instanceof UsernameNotFoundException;
    }

    private void registerWithOpenId(String username) {
        ru.skalodrom_rf.model.User u=new ru.skalodrom_rf.model.User();
        u.setLogin(username);
        u.setPassword("openid password");
        userDao.create(u);
    }
}
