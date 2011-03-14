package ru.skalodrom_rf.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.UserDao;

import javax.annotation.Resource;

/**.
 */
public class HibernateUserDetailsService implements UserDetailsService {

    @Resource
    private UserDao userDao;

    public HibernateUserDetailsService() {
    }
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User springSecurityUser;
        final ru.skalodrom_rf.model.User user = userDao.get(username);
        if(user==null){
             throw new UsernameNotFoundException( "HibernateUserDetailsService.notFound Username "+ username+ " not found");
        }

        final GrantedAuthority[] userRole = {new GrantedAuthorityImpl("ROLE_USER")};
        final GrantedAuthority[] notActivatedRole = {new GrantedAuthorityImpl("ROLE_NOT_ACTIVATED_USER")};



        if(user.getActivationCode()==null){
            springSecurityUser = new User(username, user.getPassword(), true, true, true, true, userRole);
        }else{
            springSecurityUser = new User(username, user.getPassword(), true, true, true, true, notActivatedRole);
        }


        return springSecurityUser;
    }
}
