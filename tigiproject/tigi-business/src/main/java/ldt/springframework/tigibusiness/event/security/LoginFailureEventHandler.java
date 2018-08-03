package ldt.springframework.tigibusiness.event.security;

import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/31/18
 */


@Component
public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public void onApplicationEvent(LoginFailureEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        System.out.println(">>> LoginEvent Failure for: " + authentication.getPrincipal());
        this.updateUserAccount(authentication);
    }

    /*
     * This method check and update the user account
     * If this account had login failure too much times
     * It will be lock forever
     */
    private void updateUserAccount(Authentication authentication){
        User user = userService.findByUserName((String) authentication.getPrincipal());

        if (user != null) { //user found
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if(user.getFailedLoginAttempts() > 5){
                // If the User Account is set to enabled = false
                // The Spring Security will never accept this account any more
                user.setEnabled(false);
            }

            System.out.println(">>> Valid User name, updating failed attempts");
            userService.saveOrUpdate(user);
        }
    }
}

