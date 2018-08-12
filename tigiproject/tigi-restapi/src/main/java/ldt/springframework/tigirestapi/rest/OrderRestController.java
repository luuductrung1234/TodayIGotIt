package ldt.springframework.tigirestapi.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Order API", description = "Operation pertaining to Order")
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

    @ApiOperation(value = "Checkout the order of current login user and payment preparation", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 500, message = "Fail to create new order"),
    })
    @GetMapping(value = "/user/order/checkout", produces = "application/json")
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


    @ApiOperation(value = "Create new order for the selected course", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 404, message = "Course not found"),
            @ApiResponse(code = 400, message = "Course is already owned"),
            @ApiResponse(code = 500, message = "Fail to create new order"),
    })
    @GetMapping(value = "/user/order/buynow/{id}", produces = "application/json")
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


    @ApiOperation(value = "Execute payment transaction", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully payment"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 400, message = "Order is empty or not available"),
            @ApiResponse(code = 500, message = "Fail in payment transaction"),
    })
    @PostMapping(value = "/user/order/pay", consumes = "application/json")
    public ResponseEntity pay(@RequestBody Order newOrder){
        if(newOrder == null)
            throw new OrderNotAvailableException();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        try{
            for (OrderDetails orderDetails:
                 newOrder.getOrderDetails()) {
                orderDetails.setOrder(newOrder);
            }
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
