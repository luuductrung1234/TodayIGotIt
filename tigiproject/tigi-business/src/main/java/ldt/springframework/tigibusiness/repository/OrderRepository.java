package ldt.springframework.tigibusiness.repository;

import ldt.springframework.tigibusiness.domain.Order;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


public interface OrderRepository extends CRUDRepository<Order>{
    List<Order> findAllBetweenDays(LocalDate dayBefore, LocalDate dayAfter);
}
