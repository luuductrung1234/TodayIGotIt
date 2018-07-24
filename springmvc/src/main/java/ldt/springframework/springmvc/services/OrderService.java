package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.Order;

import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */

public interface OrderService extends CRUDService<Order> {
    BigDecimal totalPrice(Order order);
}
