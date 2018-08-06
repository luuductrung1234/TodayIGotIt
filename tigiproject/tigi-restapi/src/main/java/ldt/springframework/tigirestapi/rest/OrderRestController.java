package ldt.springframework.tigirestapi.rest;


import io.swagger.annotations.Api;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.services.CartService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.OrderService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.cart.CourseAlreadyHaveException;
import ldt.springframework.tigirestapi.exception.order.CartIsEmptyException;
import ldt.springframework.tigirestapi.exception.order.OrderNotAvailableException;
import ldt.springframework.tigirestapi.exception.order.PaymentFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/4/18
 */

@RestController
@RequestMapping("/api")
@Api(value = "Order API", description = "Operations pertaining to Order of current login User")
public class OrderRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;



    // =======================================
    // =          Attribute Section          =
    // =======================================

    private boolean isSinglePay = false;


    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @GetMapping(value = "/user/order/checkout")
    public Order checkOut(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());
        if (cartService.cartIsEmpty(curUser.getCart())) {
            throw new CartIsEmptyException();
        }

        Order newOrder = cartService.convertCartToOrder(curUser.getCart(), curUser);

        isSinglePay = false;
        return newOrder;
    }

    @GetMapping(value = "/user/order/buynow/{id}")
    public Order buyNow(@PathVariable("id") Integer courseId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());
        if(userService.checkCourseOwned(curUser, courseId)) {
            throw new CourseAlreadyHaveException();
        }

        Course course = courseService.getById(courseId);

        // create a temp cart
        Cart newCart = new Cart();
        newCart.addCartDetail(new CartDetails(1, course));
        // convert temp cart to new order
        Order newOrder = cartService.convertCartToOrder(newCart, curUser);

        isSinglePay = true;
        return newOrder;
    }

    @PostMapping(value = "/user/order/pay")
    public ResponseEntity pay(@RequestBody Order newOrder){
        if(newOrder == null)
            throw new OrderNotAvailableException();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        try{
            orderService.pay(isSinglePay, curUser, newOrder);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new PaymentFailException();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/user/show")
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
