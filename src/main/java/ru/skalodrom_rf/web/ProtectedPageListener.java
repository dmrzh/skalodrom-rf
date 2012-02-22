package ru.skalodrom_rf.web;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.application.IComponentInstantiationListener;
import ru.skalodrom_rf.UserService;
import ru.skalodrom_rf.web.pages.LoginPage;
import ru.skalodrom_rf.web.pages.ProfileEditPage;

import javax.annotation.Resource;

/**.*/
class ProtectedPageListener implements IComponentInstantiationListener {
    @Resource
    public UserService userService;

    @Override
    public void onInstantiation(Component component) {
        final AuthenticatedUser classAnnotation = component.getClass().getAnnotation(AuthenticatedUser.class);
        if (classAnnotation != null ) {
            if(userService.isUserAnonymous()){
                throw new RestartResponseException(LoginPage.class);
            }else if (!hasEmail()){
                 throw new RestartResponseException(ProfileEditPage.class);
            }
        }
    }
      private boolean hasEmail() {
        String email = userService.getCurrentUser().getProfile().getEmail().getAddress();
        return email!=null && email.length()>0;
    }
}
