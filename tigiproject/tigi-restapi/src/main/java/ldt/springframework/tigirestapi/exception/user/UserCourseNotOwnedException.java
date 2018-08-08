package ldt.springframework.tigirestapi.exception.user;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public class UserCourseNotOwnedException extends RuntimeException{
    public UserCourseNotOwnedException(){
        super("Current User not owned this course!");
    }
}
