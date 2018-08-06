package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */

public class UserCreateFailException extends RuntimeException{
    public UserCreateFailException(){
        super("Something went wrong! Fail to create new User");
    }
}
