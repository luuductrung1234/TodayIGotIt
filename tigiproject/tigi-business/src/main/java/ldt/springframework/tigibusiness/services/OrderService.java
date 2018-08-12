package ldt.springframework.tigibusiness.services;

import ldt.springframework.tigibusiness.commands.statistic.ReceiptByDay;
import ldt.springframework.tigibusiness.commands.statistic.ReceiptByMonth;
import ldt.springframework.tigibusiness.commands.statistic.ReceiptByYear;
import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.domain.User;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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

    User pay(boolean isSinglePay, User curUser, Order newOrder);

    List<ReceiptByDay> receiptByDay(int modify);

    List<ReceiptByMonth> receiptByMonth(int modify);

    List<ReceiptByYear> receiptByYear(int modify);
}
