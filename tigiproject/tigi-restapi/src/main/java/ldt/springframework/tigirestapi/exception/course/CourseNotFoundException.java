package ldt.springframework.tigirestapi.exception.course;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String message){
        super("Filter : " + message);
    }
}
