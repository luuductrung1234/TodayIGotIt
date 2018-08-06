package ldt.springframework.tigirestapi.exception.order;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class OrderNotAvailableException extends RuntimeException{
    public OrderNotAvailableException(){
        super("Order is not available to execute the payment progress!");
    }
}
