package ldt.springframework.tigibusiness.repository.springdatarepository.data;

import ldt.springframework.tigibusiness.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */
public interface OrderSpringData extends CrudRepository<Order, Integer> {

    List<Order> findAllByDateCreatedAfterAndDateCreatedBefore(LocalDate dataBefore, LocalDate dateAfter);
}
