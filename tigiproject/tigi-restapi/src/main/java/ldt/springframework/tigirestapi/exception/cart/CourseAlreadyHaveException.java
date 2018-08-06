package ldt.springframework.tigirestapi.exception.cart;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class CourseAlreadyHaveException extends RuntimeException{
    public CourseAlreadyHaveException(){
        super("Course already belong to current user!");
    }
}
