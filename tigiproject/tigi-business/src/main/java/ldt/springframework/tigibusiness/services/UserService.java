package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.User;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public interface UserService extends CRUDService<User>{

    User login(String username, String password);

    User findByUserName(String userName);

    void updateLoginUserDataToSession(HttpServletRequest request, CartService cartService, User loginUser);

    void updateCurrentUserDataToSession(HttpServletRequest request, CartService cartService, Integer userId);

    void updateLogoutUserToSession(WebRequest request, SessionStatus status);

    User saveOrUpdateUserForm(UserForm userForm);
}