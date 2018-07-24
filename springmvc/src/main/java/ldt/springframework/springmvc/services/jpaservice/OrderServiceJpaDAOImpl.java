package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.domain.Order;
import ldt.springframework.springmvc.domain.OrderDetails;
import ldt.springframework.springmvc.services.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
public class OrderServiceJpaDAOImpl extends AbstractJpaDAOService
    implements OrderService {

    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Order> result = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Order getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Order result = em.find(Order.class, id);
        em.close();

        return result;
    }

    @Override
    public Order saveOrUpdate(Order order) {
        EntityManager em = emf.createEntityManager();

        Order savedOrder = null;
        try {
            savedOrder = this.doInTransaction(em,() -> em.merge(order));
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedOrder;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em,() -> {
                em.remove(em.find(Order.class, id));

                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
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
