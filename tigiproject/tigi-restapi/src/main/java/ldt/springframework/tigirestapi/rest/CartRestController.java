package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.CartService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.cart.CourseAlreadyHaveException;
import ldt.springframework.tigirestapi.exception.user.UserNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "Cart API", description = "Operations pertaining to Cart Information of current login User")
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

    @GetMapping(value = "/user/cart/add/{id}")
    public Cart addToCart(@PathVariable("id") Integer courseId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());
        if(userService.checkCourseOwned(curUser, courseId)) {
            throw new CourseAlreadyHaveException();
        }

        Course course = courseService.getById(courseId);
        cartService.addToCart(curUser, course);
        try {
            curUser = userService.saveOrUpdate(curUser);
        }catch (Exception ex){
            throw new UserNotAvailableException();
        }

        return curUser.getCart();
    }

    @GetMapping(value = "/user/cart/remove/{id}")
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
