package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.CartService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.cart.CourseAlreadyHaveException;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.exception.user.UserNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/4/18
 */


@RestController
@RequestMapping("/api")
@Api(value = "Cart API", description = "Manipulate Cart of current login User")
public class CartRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseService courseService;

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    private UserFormConverter userFormConverter;



    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @ApiOperation(value = "Add selected course to cart", response = Cart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add to cart"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 404, message = "Course not found"),
            @ApiResponse(code = 400, message = "Course is already owned"),
            @ApiResponse(code = 500, message = "The current user was impacted by another progress"),
    })
    @GetMapping(value = "/user/cart/add/{id}", produces = "application/json")
    public Cart addToCart(@PathVariable("id") Integer courseId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());
        if(userService.checkCourseOwned(curUser, courseId)) {
            throw new CourseAlreadyHaveException();
        }

        Course course = courseService.getById(courseId);
        if(course == null){
            throw new CourseNotFoundException(courseId.toString());
        }

        cartService.addToCart(curUser, course);
        try {
            curUser = userService.saveOrUpdate(curUser);
        }catch (Exception ex){
            throw new UserNotAvailableException();
        }

        return curUser.getCart();
    }

    @ApiOperation(value = "Remove selected course from cart", response = Cart.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully remove from cart"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 500, message = "The current user was impacted by another progress"),
    })
    @GetMapping(value = "/user/cart/remove/{id}", produces = "application/json")
    public Cart removeFromCart(@PathVariable("id") Integer courseId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        cartService.removeFromCart(curUser, courseId);
        try {
            curUser = userService.saveOrUpdate(curUser);
        }catch (Exception ex){
            throw new UserNotAvailableException();
        }

        return curUser.getCart();
    }
}
