package ldt.springframework.springmvc.sercurity;

import ldt.springframework.springmvc.services.CartService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/29/18
 */


public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //do some logic here if you want something to be done whenever
        //the user successfully logs in.

        UserDetails authUser = (UserDetails) authentication.getPrincipal();
        userService.updateLoginUserDataToSession(httpServletRequest, cartService, userService.findByUserName(authUser.getUsername()));

        //set our response to OK status
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        httpServletResponse.sendRedirect("/TigiProject/");
    }
}
