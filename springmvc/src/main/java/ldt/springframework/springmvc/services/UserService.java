package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.User;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public interface UserService extends CRUDService<User>{

    public User login(String username, String password);
}
