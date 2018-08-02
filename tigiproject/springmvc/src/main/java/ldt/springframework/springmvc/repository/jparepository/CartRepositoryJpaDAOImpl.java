package ldt.springframework.springmvc.repository.jparepository;

import ldt.springframework.springmvc.domain.Cart;
import ldt.springframework.springmvc.repository.CartRepository;
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
public class CartRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements CartRepository {

    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Cart> result = em.createQuery("SELECT c FROM Cart c", Cart.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Cart getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Cart result = em.find(Cart.class, id);
        em.close();

        return result;
    }

    @Override
    public Cart saveOrUpdate(Cart cart) {
        EntityManager em = emf.createEntityManager();

        Cart savedCart = null;
        try {
            savedCart = this.doInTransaction(em,() -> em.merge(cart));
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedCart;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em,() -> {
                em.remove(em.find(Cart.class, id));

                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }
}
