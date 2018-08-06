package ldt.springframework.tigirestapi.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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


    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @GetMapping(value = "/user/info")
    public Resource<User> showUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();


        // HATEOAS
        Resource<User> resource = new Resource<>(curUser);
        ControllerLinkBuilder linkToUserCart = linkTo(methodOn(this.getClass()).showUserCart());
        ControllerLinkBuilder linkToUserCourses = linkTo(methodOn(this.getClass()).showUserCourses());
        ControllerLinkBuilder linkToUserOrders = linkTo(methodOn(this.getClass()).showUserOrders());
        resource.add(linkToUserCart.withRel("curUser-cart"));
        resource.add(linkToUserCourses.withRel("curUser-courses"));
        resource.add(linkToUserOrders.withRel("curUser-orders"));

        return resource;
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
    public List<Order> showUserOrders(){
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
    public UserForm updateUserInfo(@Valid @RequestBody UserForm userForm){

        if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
            throw new PasswordNotMatchException(userForm.getPasswordText(), userForm.getPasswordTextConf());
        }

        try {
            User savedUser = userService.updateUserForm(userForm);
            if(savedUser == null){
                throw new UserUpdateFailException(userForm.getUserId().toString());
            }

            SecurityContextHolder.getContext().setAuthentication(null);
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

    @DeleteMapping(value = "/user/delete")
    public ResponseEntity removeUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        try {
            userService.delete(curUser.getId());

            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            throw new UserNotAvailableException();
        }
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
    public Resource<User> getUserByUsername(@PathVariable String username){
        User user = userService.findByUserName(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }

        // HATEOAS
        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUser());
        resource.add(linkTo.withRel("all-users"));

        return resource;
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
