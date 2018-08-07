package ldt.springframework.tigirestapi.exhandler;

import ldt.springframework.tigirestapi.exception.cart.CourseAlreadyHaveException;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.exception.course.CourseSaveFailException;
import ldt.springframework.tigirestapi.exception.order.CartIsEmptyException;
import ldt.springframework.tigirestapi.exception.order.OrderNotAvailableException;
import ldt.springframework.tigirestapi.exception.order.PaymentFailException;
import ldt.springframework.tigirestapi.exception.user.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    // =======================================
    // =      General Exception Handler      =
    // =======================================

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex,
                                                    WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        ex.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



    // =======================================
    // =        User Exception Handler       =
    // =======================================

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(Exception ex,
                                                    WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public final ResponseEntity handlePasswordNotMatchException(Exception ex,
                                                            WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUpdateFailException.class)
    public final ResponseEntity handleUserUpdateFailException(Exception ex,
                                                            WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserCreateFailException.class)
    public final ResponseEntity handleUserCreateFailException(Exception ex,
                                                            WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotAvailableException.class)
    public final ResponseEntity handleUserNotAvailableException(Exception ex,
                                                              WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }





    // =======================================
    // =       Course Exception Handler      =
    // =======================================

    @ExceptionHandler(CourseNotFoundException.class)
    public final ResponseEntity handleCourseNotFoundException(Exception ex,
                                                            WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseSaveFailException.class)
    public final ResponseEntity handleCourseSaveFailException(Exception ex,
                                                              WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    // =======================================
    // =        Cart Exception Handler       =
    // =======================================

    @ExceptionHandler(CourseAlreadyHaveException.class)
    public final ResponseEntity handleCourseAlreadyHaveException(Exception ex,
                                                           WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }





    // =======================================
    // =       Order Exception Handler       =
    // =======================================

    @ExceptionHandler(CartIsEmptyException.class)
    public final ResponseEntity handleCartIsEmptyException(Exception ex,
                                                              WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotAvailableException.class)
    public final ResponseEntity handleOrderNotAvailableException(Exception ex,
                                                           WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentFailException.class)
    public final ResponseEntity handlePaymentFailException(Exception ex,
                                                                 WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
