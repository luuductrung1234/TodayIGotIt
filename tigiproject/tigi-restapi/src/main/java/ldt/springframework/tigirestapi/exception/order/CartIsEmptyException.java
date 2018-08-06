package ldt.springframework.tigirestapi.exception.order;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/6/18
 */
public class CartIsEmptyException extends RuntimeException{
    public CartIsEmptyException(){
        super("Cart is empty! Prepare for payment fail.");
    }
}
