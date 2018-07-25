package ldt.springframework.springmvc.repository.jparepository;

import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.repository.UserRepository;
import ldt.springframework.springmvc.services.sercurity.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile("jpadao")
public class UserRepositoryJpaDAOImpl extends AbstractJpaDAORepository
        implements UserRepository {

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
        List<User> result = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        em.close();

        return result;
    }

    @Override
    public User getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        User result = em.find(User.class, id);
        em.close();

        return result;
    }

    @Override
    public User saveOrUpdate(User user) {
        EntityManager em = emf.createEntityManager();

        User savedUser = null;
        try {
            savedUser = this.doInTransaction(em, () -> {
                if(user.getPassword() != null){
                    user.setEncryptedPassowrd(encryptionService.encryptString(user.getPassword()));
                }

                return em.merge(user);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
        return savedUser;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        try {
            this.doInTransaction(em, () -> {
                em.remove(em.find(User.class, id));
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }

    @Override
    public User checkUsernamePassword(String username, String password) {
        // get User with username
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username);
        User getUser = null;
        try{
            getUser = (User) query.getSingleResult();
        }catch (Exception ex){
            return null;
        }

        // check password
        if(encryptionService.checkPassword(password, getUser.getEncryptedPassowrd())){
            return getUser;
        }

        return null;
    }
}
