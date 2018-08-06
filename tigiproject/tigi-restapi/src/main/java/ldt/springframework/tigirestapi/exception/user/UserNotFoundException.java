package ldt.springframework.tigirestapi.exception.user;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */


public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String message){
        super("Username: " + message);
    }
}
