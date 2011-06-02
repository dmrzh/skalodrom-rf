package ru.skalodrom_rf.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.UserDao;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class OpenIDAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (isOpenIdRegistration(exception)) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            request.getSession(true).setAttribute("USER_OPENID_CREDENTIAL", exception.getExtraInformation());
            OpenIDAuthenticationToken authentication = (OpenIDAuthenticationToken) exception.getAuthentication();
            registerWithOpenId(authentication.getIdentityUrl()) ;
            redirectStrategy.sendRedirect(request, response, "/profileEdit.html");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    private boolean isOpenIdRegistration(AuthenticationException exception) {

        boolean notFound = exception instanceof UsernameNotFoundException;
        boolean openIdToken = exception.getAuthentication() instanceof OpenIDAuthenticationToken;
        OpenIDAuthenticationToken authentication = (OpenIDAuthenticationToken) exception.getAuthentication();
        boolean openIdSuccess = authentication.getStatus().equals(OpenIDAuthenticationStatus.SUCCESS);
        return notFound && openIdToken && openIdSuccess;
    }
    private void registerWithOpenId(String username) {
        ru.skalodrom_rf.model.User u=new ru.skalodrom_rf.model.User();
        u.setLogin(username);
        u.setPassword("openid password");
        userDao.create(u);
    }
}
