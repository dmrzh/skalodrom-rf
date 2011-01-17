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
        if(userDao.get(username)==null){
             throw new UsernameNotFoundException( "HibernateUserDetailsService.notFound Username "+ username+ " not found");
        }
        final GrantedAuthority[] authorities = {new GrantedAuthorityImpl("ROLE_USER")};
        return new User(username, userDao.get(username).getPassword(),true,true,true,true, authorities);
    }
}
