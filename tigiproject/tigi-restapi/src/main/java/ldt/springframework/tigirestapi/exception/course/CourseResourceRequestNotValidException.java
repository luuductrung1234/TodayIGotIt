package ldt.springframework.tigirestapi.exception.course;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public class CourseResourceRequestNotValidException extends RuntimeException{
    public CourseResourceRequestNotValidException(){
        super("Course Resource is not valid for this kind of request!");
    }
}
