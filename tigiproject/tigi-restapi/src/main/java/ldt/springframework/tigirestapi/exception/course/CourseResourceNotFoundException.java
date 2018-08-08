package ldt.springframework.tigirestapi.exception.course;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */
public class CourseResourceNotFoundException extends RuntimeException{
    public CourseResourceNotFoundException(){
        super("Course Resource not found!");
    }
}
