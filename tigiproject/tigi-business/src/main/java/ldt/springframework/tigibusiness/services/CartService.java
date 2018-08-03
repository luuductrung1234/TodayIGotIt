package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.domain.User;

import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


public interface CartService  extends CRUDService<Cart>{
    int getContentCount(User user);

    void addToCart(User user, Course course);

    void removeFromCart(User user, Integer courseId);

    Order convertCartToOrder(Cart cart, User user);

    BigDecimal calculateTotalPrice(Cart cart);

    boolean cartIsEmpty(Cart cart);

    void clearCart(User user);
}
