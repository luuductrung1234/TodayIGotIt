package ldt.springframework.tigirestapi.rest;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.security.TiGiAuthService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/user/info")
    public UserForm showUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        return userFormConverter.revert(curUser);
    }

    @GetMapping(value = "/user/info/cart")
    public Cart showUserCart(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        return curUser.getCart();
    }

    @GetMapping(value = "/user/info/orders")
    public List<Order> showUserOrder(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        return curUser.getOrders();
    }

    @GetMapping(value = "/user/info/courses")
    public List<Course> showUserCourses(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        List<Course> listCourse = new ArrayList<>();
        for (CourseOwner courseOwner:
             curUser.getCourseOwners()) {
            listCourse.add(courseOwner.getCourse());
        }

        return listCourse;
    }

    @PostMapping(value = "/user/update")
    public UserForm updateUserInfo(@RequestBody UserForm userForm){

        if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
            throw new PasswordNotMatchException(userForm.getPasswordText(), userForm.getPasswordTextConf());
        }

        try {
            User savedUser = userService.updateUserForm(userForm);
            if(savedUser == null){
                throw new UserUpdateFailException(userForm.getUserId().toString());
            }

            return userFormConverter.revert(savedUser);
        } catch (Exception ex) {
            ex.getStackTrace();
            throw new UserUpdateFailException(userForm.getUserId().toString());
        }
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
        User user = userService.findByUserName(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }

        return userFormConverter.revertToFewInfo(user);
    }

    @PostMapping(value = "/user/new")
    public ResponseEntity createNewUser(@RequestBody UserForm userForm){

        if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
            throw new PasswordNotMatchException(userForm.getPasswordText(), userForm.getPasswordTextConf());
        }

        try {
            // reset user id to prevent from updating the existed user
            userForm.setUserId(null);
            User savedUser = userService.saveUserForm(userForm);

            if(savedUser == null){
                throw new UserCreateFailException();
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/user/show")
                    .build()
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            ex.getStackTrace();
            throw new UserCreateFailException();
        }
    }
}
