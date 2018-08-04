package ldt.springframework.tigirestapi.rest;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.Customer;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.security.TiGiAuthService;
import ldt.springframework.tigibusiness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/1/18
 */


@RestController
@RequestMapping("/api")
public class UserRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private UserFormConverter userFormConverter;

    @Autowired
    private TiGiAuthService tiGiAuthService;



    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/user/info")
    public UserForm showUser(){
        return tiGiAuthService.sessionCheckLogin(null, () ->{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userFormConverter.revert(userService.findByUserName(userDetails.getUsername()));
        });
    }

    @GetMapping(value = "/users/full")
    public List<UserForm> getAllUserWithFullInfo(){
        List<UserForm> userForms = new ArrayList<>();
        for (User user:
                (List<User>) userService.listAll()) {
            userForms.add(userFormConverter.revert(user));
        }

        return userForms;
    }


    // =======================================
    // =         Non-Auth REST Method        =
    // =======================================

    @GetMapping(value = "/users")
    public List<UserForm> getAllUser(){
        List<UserForm> userForms = new ArrayList<>();
        for (User user:
             (List<User>) userService.listAll()) {
            userForms.add(userFormConverter.revertToFewInfo(user));
        }

        return userForms;
    }

    @GetMapping(value = "/users/count")
    public int countUsers(){
        return userService.listAll().size();
    }


    @GetMapping(value = "/user/find/{username}")
    public Customer getUserByUsername(@PathVariable String username){
        return userService.findByUserName(username).getCustomer();
    }

    @PostMapping(value = "/user/new")
    public UserForm createNewUser(@RequestBody UserForm userForm){
        try {
            if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
                return null;
            }
            User savedUser = userService.saveOrUpdateUserForm(userForm);

           return userFormConverter.revert(savedUser);
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return null;
    }
}
