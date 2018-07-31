package ldt.springframework.springmvc.repository.jparepository;

import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.repository.CustomerRepository;
import ldt.springframework.springmvc.services.sercurity.encrypt.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements CustomerRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private EncryptionService encryptionService;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        EntityManager em = emf.createEntityManager();
        List<Customer> result = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public Customer getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        Customer result = em.find(Customer.class, id);
        em.close();

        return result;
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        EntityManager em = emf.createEntityManager();

        Customer savedCustomer = null;
        try {
            savedCustomer = this.doInTransaction(em,() -> {
                if(customer.getUser() != null && customer.getUser().getPassword() != null){
                    customer.getUser().setEncryptedPassowrd(
                            encryptionService.encryptString(customer.getUser().getPassword()));
                }
                return em.merge(customer);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedCustomer;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em,() -> {
                em.remove(em.find(Customer.class, id));

                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }
}
