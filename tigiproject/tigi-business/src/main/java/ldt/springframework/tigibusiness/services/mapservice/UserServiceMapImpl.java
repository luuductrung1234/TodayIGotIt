package ldt.springframework.tigibusiness.services.mapservice;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.domain.DomainObject;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.CartService;
import ldt.springframework.tigibusiness.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */


@Service
@Profile("map")
public class UserServiceMapImpl extends AbstractMapService
        implements UserService {
    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public User getById(Integer id) {
        return (User) super.getById(id);
    }

    @Override
    public User saveOrUpdate(User user) {
        return (User) super.saveOrUpdateDomainObject(user);
    }

    @Override
    public void delete(Integer id) {
        super.deleteDomainObject(id);
    }

    @Override
    public User login(String username, String password) {

        List<DomainObject> users = listAll();
        for (DomainObject domainObject : users) {
            User user = (User) domainObject;

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User findByUserName(String userName) {
        Optional returnUser = domainMap.values().stream().filter(new Predicate<DomainObject>() {
            @Override
            public boolean test(DomainObject domainObject) {
                return false;
            }
        }).findFirst();

        return (User) returnUser.get();
    }


    @Override
    public void updateLoginUserDataToSession(HttpServletRequest request, CartService cartService, User loginUser) {

    }

    @Override
    public void updateCurrentUserDataToSession(HttpServletRequest request, CartService cartService, Integer userId) {

    }

    @Override
    public void updateLogoutUserToSession(WebRequest request, SessionStatus status) {

    }

    @Override
    public User saveOrUpdateUserForm(UserForm userForm) {
        return null;
    }
}
