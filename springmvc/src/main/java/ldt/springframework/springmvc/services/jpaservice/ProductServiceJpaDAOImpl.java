package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.domain.Product;
import ldt.springframework.springmvc.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */

@Service
@Profile("jpadao")
public class ProductServiceJpaDAOImpl extends AbstractJpaDAOService
                                        implements ProductService {

    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Product> result = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Product result = em.find(Product.class, id);
        em.close();

        return result;
    }

    @Override
    public Product saveOrUpdate(Product product) {
        EntityManager em = emf.createEntityManager();

        Product savedProduct = null;
        try {
            savedProduct = this.doInTransaction(em, () -> em.merge(product));
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedProduct;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em, () -> {
                em.remove(em.find(Product.class, id));
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }
}
