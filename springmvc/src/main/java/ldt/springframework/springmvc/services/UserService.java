package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.User;

import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public interface UserService extends CRUDService<User>{

    User login(String username, String password);

    void updateLoginUserDataToSession(HttpServletRequest request, CartService cartService, User loginUser);

    void updateCurrentUserDataToSession(HttpServletRequest request, CartService cartService, Integer userId);
}
