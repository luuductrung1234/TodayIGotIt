package ldt.springframework.tigibusiness.security.aop;

import ldt.springframework.tigibusiness.event.security.LoginFailureEvent;
import ldt.springframework.tigibusiness.event.security.LoginFailureEventPublisher;
import ldt.springframework.tigibusiness.event.security.LoginSuccessEvent;
import ldt.springframework.tigibusiness.event.security.LoginSuccessEventPublisher;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/30/18
 */


@Aspect
@Component
public class LoginAspect {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private LoginFailureEventPublisher loginFailureEventPublisher;

    @Autowired
    private LoginSuccessEventPublisher loginSuccessEventPublisher;

    // =======================================
    // =           Aspect Methods            =
    // =======================================
    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate() {
    }

    @Before("ldt.springframework.tigibusiness.security.aop.LoginAspect.doAuthenticate() && args(authentication)")
    public void loginBefore(Authentication authentication) {
        System.out.println(">>> This is before the Authenticate Method: Authentication: " + authentication.isAuthenticated());
    }

    @AfterReturning(value = "ldt.springframework.tigibusiness.security.aop.LoginAspect.doAuthenticate()",
            returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication) {
        // publishing the login success event
        System.out.println(">>> Publishing login Success event");
        loginSuccessEventPublisher.publish(new LoginSuccessEvent(authentication));
    }

    @AfterThrowing("ldt.springframework.tigibusiness.security.aop.LoginAspect.doAuthenticate() " +
            "&& args(authentication)")
    public void logAuthenicationException(Authentication authentication) {

        // publishing the login failure event
        System.out.println(">>> Publishing login failure event");
        loginFailureEventPublisher.publish(new LoginFailureEvent(authentication));
    }
}
