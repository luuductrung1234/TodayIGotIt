package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CartService;
import ldt.springframework.springmvc.services.UserService;
import ldt.springframework.springmvc.services.sercurity.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */


@Service
@Profile("jpadao")
public class UserServiceJpaDAOImpl extends AbstractJpaDAOService
                                    implements UserService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private EncryptionService encryptionService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    public User login(String username, String password){

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
        User resutl = em.find(User.class, id);
        em.close();

        return resutl;
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
    public void updateLoginUserDataToSession(HttpServletRequest request, CartService cartService, User loginUser) {
        // there is a login action
        request.getSession().setAttribute("curUser", loginUser);
        request.getSession().setAttribute("curUserFirstName", loginUser.getCustomer().getFirstName());
        request.getSession().setAttribute("curUserCartCount", cartService.getContentCount(loginUser));
    }

    @Override
    public void updateCurrentUserDataToSession(HttpServletRequest request, CartService cartService, Integer userId) {
        // there is a update action for the current user, who is already login
        User currentUser = this.getById(userId);
        request.getSession().setAttribute("curUser", currentUser);
        request.getSession().setAttribute("curUserFirstName", currentUser.getCustomer().getFirstName());
        request.getSession().setAttribute("curUserCartCount", cartService.getContentCount(currentUser));
    }

    @Override
    public void updateLogoutUserToSession(WebRequest request, SessionStatus status){
        status.setComplete();
        request.removeAttribute("curUser", WebRequest.SCOPE_SESSION);
        request.removeAttribute("curUserFirstName", WebRequest.SCOPE_SESSION);
        request.removeAttribute("curUserCartCount", WebRequest.SCOPE_SESSION);
        request.removeAttribute("curUserOrder", WebRequest.SCOPE_SESSION);
    }
}
