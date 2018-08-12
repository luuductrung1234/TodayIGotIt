package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.repository.OrderRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.OrderSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile(value = {"springdatajpa", "jpadao"})
public class OrderRepositorySpringDataImpl implements OrderRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private OrderSpringData orderSpringData;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        List<Order> orders = new ArrayList<>();
        orderSpringData.findAll().forEach(orders::add);

        return orders;
    }

    @Override
    public Order getById(Integer id) {
        if(orderSpringData.findById(id).isPresent()){
            return orderSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return orderSpringData.save(order);
    }

    @Override
    public void delete(Integer id) {
        orderSpringData.deleteById(id);
    }


    @Override
    public List<Order> findAllBetweenDays(LocalDate dayBefore, LocalDate dayAfter) {
        return orderSpringData.findAllByDateCreatedAfterAndDateCreatedBefore(dayBefore, dayAfter);
    }
}
