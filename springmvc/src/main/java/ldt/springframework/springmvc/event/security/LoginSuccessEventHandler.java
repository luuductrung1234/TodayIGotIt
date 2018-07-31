package ldt.springframework.springmvc.event.security;

import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/31/18
 */

@Component
public class LoginSuccessEventHandler implements ApplicationListener<LoginSuccessEvent> {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        System.out.println(">>> This is after the Authenticate Method authentication: " + authentication.isAuthenticated());
        this.updateUserAccount(authentication);
    }

    private void updateUserAccount(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        if (user != null){
            user.setFailedLoginAttempts(0);
            System.out.println(">>> Valid User name, updating success attemps");
            userService.saveOrUpdate(user);
        }
    }
}
