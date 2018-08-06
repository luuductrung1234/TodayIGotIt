package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.domain.User;

import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */

public interface OrderService extends CRUDService<Order> {
    BigDecimal calculateTotalPrice(Order order);

    void increaseQuantity(Order order, Integer courseId);

    void decreaseQuantity(Order order, Integer courseId);

    void removeFromOrder(Order order, Integer courseId);

    boolean orderIsEmpty(Order order);

    void pay(boolean isSinglePay, User curUser, Order newOrder);
}
