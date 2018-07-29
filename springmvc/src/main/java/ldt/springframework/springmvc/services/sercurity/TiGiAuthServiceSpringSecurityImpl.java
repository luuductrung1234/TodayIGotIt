package ldt.springframework.springmvc.services.sercurity;

import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/29/18
 */


@Service("userDetailsService")
public class TiGiAuthServiceSpringSecurityImpl implements UserDetailsService, TiGiAuthService{

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "userToUserDetails")
    private Converter<User, UserDetailsImpl> userUserDetailsConverter;

    @Autowired
    String encodeId;

    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsImpl userDetails = userUserDetailsConverter.convert(userService.findByUserName(username));

        // specify the PasswordEncoder for Spring Security
        userDetails.setPassword("{" + encodeId + "}" + userDetails.getPassword());

        return userDetails;
    }

    @Override
    public <T> T sessionCheckLogin (T fallbackResult, Callable<T> todo){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            try {
                return todo.call();
            } catch (Exception e) {
                e.printStackTrace();
                return fallbackResult;
            }
        }

        return fallbackResult;
    }
}
