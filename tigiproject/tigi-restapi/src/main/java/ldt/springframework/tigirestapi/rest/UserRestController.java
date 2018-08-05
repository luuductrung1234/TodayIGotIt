package ldt.springframework.tigirestapi.rest;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
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

    //@CrossOrigin(origins = "*")
    @GetMapping(value = "/user/info")
    public UserForm showUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());

        return userFormConverter.revert(user);
    }

    @PostMapping(value = "/user/update")
    public UserForm updateUserInfo(@RequestBody UserForm userForm){
        try {
            if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
                // TODO : Fail Handling
                return null;
            }
            User savedUser = userService.updateUserForm(userForm);

            if(savedUser == null){
                // TODO : Fail Handling
                return null;
            }
            return userFormConverter.revert(savedUser);
        } catch (Exception ex) {
            // TODO : Fail Handling
            ex.getStackTrace();
        }

        return null;
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
    public UserForm getUserByUsername(@PathVariable String username){
        return userFormConverter.revertToFewInfo(userService.findByUserName(username));
    }

    @PostMapping(value = "/user/new")
    public UserForm createNewUser(@RequestBody UserForm userForm){
        try {
            if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
                // TODO : Fail Handling
                return null;
            }

            // reset user id to prevent from updating the existed user
            userForm.setUserId(null);
            User savedUser = userService.saveUserForm(userForm);

            if(savedUser == null){
                // TODO : Fail Handling
                return null;
            }
           return userFormConverter.revert(savedUser);
        } catch (Exception ex) {
            // TODO : Fail Handling
            ex.getStackTrace();
        }

        return null;
    }
}
