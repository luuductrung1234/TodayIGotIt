package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public class UserGetTrackingFailException extends RuntimeException{
    public UserGetTrackingFailException(){
        super("Something went wrong! Get user's tracking fail");
    }
}
