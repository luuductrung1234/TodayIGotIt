package ldt.springframework.tigirestapi.exception.order;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class PaymentFailException extends RuntimeException{
    public PaymentFailException(){
        super("Something went wrong! Can not complete the payment process.");
    }
}
