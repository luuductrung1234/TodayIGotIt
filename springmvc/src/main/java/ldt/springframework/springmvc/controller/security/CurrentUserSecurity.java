package ldt.springframework.springmvc.controller.security;

import ldt.springframework.springmvc.domain.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/26/18
 */


@Component
public class CurrentUserSecurity {
    public <T> T sessionCheckLogin (HttpServletRequest request, T fallbackResult, Callable<T> todo){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if (currentUser == null) {
            return fallbackResult;
        }

        try {
            return todo.call();
        } catch (Exception e) {
            e.printStackTrace();
            return fallbackResult;
        }
    }
}
