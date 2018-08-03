package ldt.springframework.tigibusiness.repository.springdatarepository;

import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.repository.UserRepository;
import ldt.springframework.tigibusiness.repository.springdatarepository.data.UserSpringData;
import ldt.springframework.tigibusiness.security.encrypt.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */

@Repository
@Profile(value = {"springdatajpa", "jpadao"})
public class UserRepositorySpringDataImpl implements UserRepository {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserSpringData userSpringData;

    @Autowired
    private EncryptionService encryptionService;


    // =======================================
    // =            CRUD Methods             =
    // =======================================

    @Override
    public User checkUsernamePassword(String usermame, String password) {
        // get User with username
        List<User> users = (List<User>) this.listAll();

        User getUser = null;
        try{
            getUser = userSpringData.findByUsername(usermame);

            // check password
            if(encryptionService.checkPassword(password, getUser.getEncryptedPassowrd())){
                return getUser;
            }

            return null;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public User findByUserName(String username) {
        return userSpringData.findByUsername(username);
    }

    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userSpringData.findAll().forEach(users::add);

        return users;
    }

    @Override
    public User getById(Integer id) {
        if(userSpringData.findById(id).isPresent()){
            return userSpringData.findById(id).get();
        }
        return null;
    }

    @Override
    public User saveOrUpdate(User user) {
        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            user.setEncryptedPassowrd(encryptionService.encryptString(user.getPassword()));
        }
        return userSpringData.save(user);
    }

    @Override
    public void delete(Integer id) {
        userSpringData.deleteById(id);
    }
}
