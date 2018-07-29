package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.commands.converters.UserFormConverter;
import ldt.springframework.springmvc.domain.Cart;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.repository.UserRepository;
import ldt.springframework.springmvc.services.CartService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

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
public class UserServiceJpaDAOImpl implements UserService {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormConverter userFormConverter;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public List<?> listAll() {
        return userRepository.listAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.saveOrUpdate(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }




    // =======================================
    // =          Business Methods           =
    // =======================================

    public User login(String username, String password){
        return userRepository.checkUsernamePassword(username, password);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
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

    @Override
    public User saveOrUpdateUserForm(UserForm userForm) {
        User newUser = userFormConverter.convert(userForm);

        //enhance if saved
        if(newUser.getId() != null){
            User existingUser = getById(newUser.getId());

            //set enabled flag from db
            newUser.setEnabled(existingUser.getEnabled());
        }else{
            newUser.setCart(new Cart());
        }

        return saveOrUpdate(newUser);

    }
}
