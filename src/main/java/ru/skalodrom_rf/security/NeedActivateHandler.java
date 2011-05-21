package ru.skalodrom_rf.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**.*/
public class NeedActivateHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof NotActivatedException){
            response.sendRedirect("message.html?error=notactivated");
        }else if (exception instanceof org.springframework.security.authentication.BadCredentialsException){
            response.sendRedirect("login.html?wrongPassword=true");
        }
    }


}
