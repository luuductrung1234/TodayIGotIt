package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.Cart;
import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.domain.Order;
import ldt.springframework.springmvc.domain.User;

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
