package ldt.springframework.tigibusiness.services.jpaservice;

import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.domain.OrderDetails;
import ldt.springframework.tigibusiness.repository.OrderRepository;
import ldt.springframework.tigibusiness.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


@Service
@Profile("jpadao")
public class OrderServiceJpaDAOImpl
    implements OrderService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private OrderRepository orderRepository;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return orderRepository.listAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.getById(id);
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return orderRepository.saveOrUpdate(order);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(id);
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public BigDecimal calculateTotalPrice(Order order){
        BigDecimal total = new BigDecimal(0);
        for (OrderDetails od:
             order.getOrderDetails()) {
            BigDecimal quan = new BigDecimal(od.getQuantity());
            total = total.add(od.getCourse().getPrice().multiply(quan));
        }

        return total;
    }

    @Override
    public void increaseQuantity(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                int quantity = od.getQuantity() + 1;
                od.setQuantity(quantity);
                return;
            }
        }
    }

    @Override
    public void decreaseQuantity(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                int quantity = od.getQuantity() - 1;
                if(quantity == 0){
                    removeFromOrder(order, courseId);
                }else {
                    od.setQuantity(quantity);
                }
                return;
            }
        }
    }

    @Override
    public void removeFromOrder(Order order, Integer courseId) {
        for (OrderDetails od:
                order.getOrderDetails()) {
            if(od.getCourse().getId().equals(courseId)){
                order.removeOrderDetail(od);
                return;
            }
        }
    }

    @Override
    public boolean orderIsEmpty(Order order) {
        int count = 0;
        if(order.getOrderDetails().isEmpty()){
            return true;
        }

        for (OrderDetails od:
                order.getOrderDetails()) {
            count += od.getQuantity();
        }
        return count == 0;
    }
}
