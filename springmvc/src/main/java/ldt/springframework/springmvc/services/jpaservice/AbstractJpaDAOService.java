package ldt.springframework.springmvc.services.jpaservice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.concurrent.Callable;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */
public class AbstractJpaDAOService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    protected EntityManagerFactory emf;


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf){
        this.emf = emf;
    }


    // =======================================
    // =          Business Methods           =
    // =======================================

    // Transaction Wrapper
    protected  <T> T doInTransaction(EntityManager em, Callable<T> func)
            throws Exception {
        em.getTransaction().begin();

        T result = func.call();

        em.getTransaction().commit();
        return result;
    }
}
