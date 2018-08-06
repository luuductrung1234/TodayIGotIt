package ldt.springframework.tigirestapi.exception.course;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/7/18
 */

public class CourseSaveFailException extends RuntimeException{
    public CourseSaveFailException(){
        super("Something went wrong! Course can not be save or update.");
    }
}
