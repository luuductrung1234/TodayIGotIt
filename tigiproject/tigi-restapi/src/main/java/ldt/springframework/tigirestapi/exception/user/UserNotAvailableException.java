package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */


public class UserNotAvailableException extends RuntimeException{
    public UserNotAvailableException(){
        super("User is not available! Problem can be caused by updated or deleted in another thread");
    }
}
