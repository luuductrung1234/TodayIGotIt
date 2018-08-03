package ldt.springframework.tigibusiness.repository.jparepository;

import ldt.springframework.tigibusiness.domain.Order;
import ldt.springframework.tigibusiness.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile("jpadao-pure")
public class OrderRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements OrderRepository {

    // =======================================
    // =            CRUD Methods             =
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
}
