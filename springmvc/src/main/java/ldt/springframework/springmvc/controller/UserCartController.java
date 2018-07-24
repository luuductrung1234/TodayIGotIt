package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.*;
import ldt.springframework.springmvc.services.CartService;
import ldt.springframework.springmvc.services.CourseService;
import ldt.springframework.springmvc.services.OrderService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


@Controller
public class UserCartController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OrderService orderService;


    // =======================================
    // =           Request Mapping           =
    // =======================================

    @RequestMapping("/user/addtocart/{id}")
    public String addToCart(@PathVariable("id") Integer courseId,
                            @RequestParam("currentPath") String currentPath,
                            HttpServletRequest request,
                            Model model){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null){
            return "redirect:/";
        }

        Course course = courseService.getById(courseId);
        cartService.addToCart(currentUser, course);
        userService.saveOrUpdate(currentUser);

        // Update current user data
        userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

        return "redirect:" + currentPath;
    }

    @RequestMapping("/user/removefromcart/{id}")
    public String removeFromCart(@PathVariable("id") Integer courseId,
                            @RequestParam("currentPath") String currentPath,
                            HttpServletRequest request,
                            Model model){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null){
            return "redirect:/";
        }

        cartService.removeFromCart(currentUser, courseId);
        userService.saveOrUpdate(currentUser);

        // Update current user data
        userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

        return "redirect:" + currentPath;
    }


    @RequestMapping("/user/checkout")
    public String checkOut(HttpServletRequest request, Model model){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null){
            return "redirect:/";
        }

        // convert current user cart to new order
        Order newOrder = cartService.convertCartToOrder(currentUser.getCart(), currentUser);

        model.addAttribute("order", newOrder);
        model.addAttribute("totalPrice", orderService.totalPrice(newOrder));

        return "view/order/preparePayment";
    }

    @RequestMapping("/user/buynow/{id}")
    public String buyNow(@PathVariable("id") Integer courseId,
                         HttpServletRequest request,
                         Model model){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null){
            return "redirect:/";
        }
        Course course = courseService.getById(courseId);

        // create a temp cart
        Cart newCart = new Cart();
        newCart.addCartDetail(new CartDetails(1, course));
        // convert temp cart to new order
        Order newOrder = cartService.convertCartToOrder(newCart, currentUser);
        model.addAttribute("totalPrice", orderService.totalPrice(newOrder));

        model.addAttribute("order", newOrder);

        return "view/order/preparePayment";
    }
}
