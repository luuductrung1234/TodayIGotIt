package ldt.springframework.springmvc.repository;

import ldt.springframework.springmvc.domain.User;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


public interface UserRepository extends CRUDRepository<User>{
    User checkUsernamePassword(String usermame, String password);
}
