package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class UserUpdateFailException extends RuntimeException{
    public UserUpdateFailException(String id){
        super("Something went wrong! Can not update for user_id : " + id);
    }
}
