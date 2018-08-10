package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */
public class UserLogoutFailException extends RuntimeException{
    public UserLogoutFailException(){
        super("Something went wrong! Logout fail!");
    }
}
