package ldt.springframework.tigirestapi.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.LearnTrackingService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
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
@Api(value = "User API", description = "Operations pertaining to User")
public class UserRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private UserFormConverter userFormConverter;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LearnTrackingService learnTrackingService;


    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @ApiOperation(value = "Show details information of current login user", response = UserForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The current user was impacted by another progress"),
    })
    @GetMapping(value = "/user/info", produces = "application/json")
    public Resource<UserForm> showUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();


        // HATEOAS
        Resource<UserForm> resource = new Resource<>(userFormConverter.revert(curUser));
        ControllerLinkBuilder linkToUserCart = linkTo(methodOn(this.getClass()).showUserCart());
        ControllerLinkBuilder linkToUserCourses = linkTo(methodOn(this.getClass()).showUserCourses());
        ControllerLinkBuilder linkToUserOrders = linkTo(methodOn(this.getClass()).showUserOrders());
        resource.add(linkToUserCart.withRel("curUser-cart"));
        resource.add(linkToUserCourses.withRel("curUser-courses"));
        resource.add(linkToUserOrders.withRel("curUser-orders"));

        return resource;
    }

    @ApiOperation(value = "Show cart and cart details of current login user", response = Cart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The current login user was impacted by another progress"),
    })
    @GetMapping(value = "/user/info/cart", produces = "application/json")
    public Cart showUserCart(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        return curUser.getCart();
    }

    @ApiOperation(value = "Show orders history of current login user", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The current user's information was impacted by another progress"),
    })
    @GetMapping(value = "/user/info/orders", produces = "application/json")
    public List<Order> showUserOrders(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        return curUser.getOrders();
    }

    @ApiOperation(value = "Show owned courses of current login user", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "The current user's information was impacted by another progress"),
    })
    @GetMapping(value = "/user/info/courses", produces = "application/json")
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

    @ApiOperation(value = "Get User tracking for specific Course", response = UserForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 404, message = "Course not found"),
            @ApiResponse(code = 400, message = "Course has not owned yet"),
            @ApiResponse(code = 500, message = "Fail to get resource"),
    })
    @RequestMapping(value = "/user/info/tracking/course/{id}", produces = "application/json")
    public List<LearnTracking> showUserLearnTrackingByCourseResource(@PathVariable("id") Integer id){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        if(!userService.checkCourseOwned(curUser, id)){
            throw new UserCourseNotOwnedException();
        }

        Course course = courseService.getById(id);
        if(course != null) {
            try{
                List<LearnTracking> result = learnTrackingService.findAllByUserAndCourse(curUser, course);

                return result;
            }catch (Exception ex){
                ex.printStackTrace();
                throw new UserGetTrackingFailException();
            }
        }else{
            throw new CourseNotFoundException(id.toString());
        }
    }


    @ApiOperation(value = "Update current login user information", response = UserForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved updated resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 400, message = "Password not match or invalid input information"),
            @ApiResponse(code = 500, message = "Update fail"),
    })
    @PostMapping(value = "/user/update", produces = "application/json")
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

    @ApiOperation(value = "List users with full information", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
    })
    @GetMapping(value = "/users/full", produces = "application/json")
    public List<UserForm> getAllUserWithFullInfo(){
        List<UserForm> userForms = new ArrayList<>();
        for (User user:
                (List<User>) userService.listAll()) {
            userForms.add(userFormConverter.revert(user));
        }

        return userForms;
    }

    @ApiOperation(value = "List users with full information", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 500, message = "The current user's information was impacted by another progress"),
    })
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

    @ApiOperation(value = "List users with few information", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    })
    @GetMapping(value = "/users", produces = "application/json")
    public List<UserForm> getAllUser(){
        List<UserForm> userForms = new ArrayList<>();
        for (User user:
             (List<User>) userService.listAll()) {
            userForms.add(userFormConverter.revertToFewInfo(user));
        }

        return userForms;
    }


    @ApiOperation(value = "Count number of users", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully count"),
    })
    @GetMapping(value = "/users/count")
    public int countUsers(){
        return userService.listAll().size();
    }


    @ApiOperation(value = "Search for user with given username", response = UserForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "The resource not found"),
    })
    @GetMapping(value = "/user/find/{username}")
    public Resource<UserForm> getUserByUsername(@PathVariable String username){
        User user = userService.findByUserName(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }

        // HATEOAS
        Resource<UserForm> resource = new Resource<>(userFormConverter.revertToFewInfo(user));
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUser());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }


    @ApiOperation(value = "Registration for new users", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 400, message = "Password not match or invalid input information"),
            @ApiResponse(code = 500, message = "Registration fail"),
    })
    @PostMapping(value = "/user/new", consumes = "application/json")
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
