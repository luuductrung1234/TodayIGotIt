package ldt.springframework.springmvc.services.mapservice;

import ldt.springframework.springmvc.domain.DomainObject;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

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
        for (DomainObject domainObject: users){
            User user = (User) domainObject;

            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return user;
            }
        }

        return null;
    }
}
