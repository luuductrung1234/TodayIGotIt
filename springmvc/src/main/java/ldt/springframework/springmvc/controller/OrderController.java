package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.controller.security.CurrentUserSecurity;
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

import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/23/18
 */


@Controller
public class OrderController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserSecurity currentUserSecurity;

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private boolean isSinglePage;


    // =======================================
    // =           Request Mapping            =
    // =======================================

    @RequestMapping("/user/checkout")
    public String checkOut(HttpServletRequest request, Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            // convert current user cart to new order
            if (cartService.cartIsEmpty(currentUser.getCart())) {
                return "redirect:/";
            }
            Order newOrder = cartService.convertCartToOrder(currentUser.getCart(), currentUser);
            request.getSession().setAttribute("curUserOrder", newOrder);

            model.addAttribute("order", newOrder);
            model.addAttribute("totalPrice", orderService.calculateTotalPrice(newOrder));

            isSinglePage = false;
            return "view/order/preparePayment";
        });
    }

    @RequestMapping("/user/buynow/{id}")
    public String buyNow(@PathVariable("id") Integer courseId,
                         HttpServletRequest request,
                         Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            Course course = courseService.getById(courseId);

            // create a temp cart
            Cart newCart = new Cart();
            newCart.addCartDetail(new CartDetails(1, course));
            // convert temp cart to new order
            Order newOrder = cartService.convertCartToOrder(newCart, currentUser);
            request.getSession().setAttribute("curUserOrder", newOrder);

            model.addAttribute("order", newOrder);
            model.addAttribute("totalPrice", orderService.calculateTotalPrice(newOrder));

            isSinglePage = true;
            return "view/order/preparePayment";
        });
    }

    @RequestMapping("user/order/plus/{id}")
    public String increaseQuantity(@PathVariable("id") Integer courseId,
                                   HttpServletRequest request,
                                   Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            Order curOrder = (Order) request.getSession().getAttribute("curUserOrder");
            if (curOrder == null) {
                return "redirect:/";
            }

            orderService.increaseQuantity(curOrder, courseId);

            model.addAttribute("order", curOrder);
            model.addAttribute("totalPrice", orderService.calculateTotalPrice(curOrder));

            return "view/order/preparePayment";
        });
    }

    @RequestMapping("user/order/minus/{id}")
    public String decreaseQuantity(@PathVariable("id") Integer courseId,
                                   HttpServletRequest request,
                                   Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            Order curOrder = (Order) request.getSession().getAttribute("curUserOrder");
            if (curOrder == null) {
                return "redirect:/";
            }

            orderService.decreaseQuantity(curOrder, courseId);
            if (orderService.orderIsEmpty(curOrder)) {
                request.getSession().setAttribute("curUserOrder", null);
                return "redirect:/";
            }

            model.addAttribute("order", curOrder);
            model.addAttribute("totalPrice", orderService.calculateTotalPrice(curOrder));

            return "view/order/preparePayment";
        });
    }

    @RequestMapping("user/order/removefromorder/{id}")
    public String removeFromOrder(@PathVariable("id") Integer courseId,
                                  HttpServletRequest request,
                                  Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            Order curOrder = (Order) request.getSession().getAttribute("curUserOrder");
            if (curOrder == null) {
                return "redirect:/";
            }

            orderService.removeFromOrder(curOrder, courseId);
            if (orderService.orderIsEmpty(curOrder)) {
                request.getSession().setAttribute("curUserOrder", null);
                return "redirect:/";
            }

            model.addAttribute("order", curOrder);
            model.addAttribute("totalPrice", orderService.calculateTotalPrice(curOrder));

            return "view/order/preparePayment";
        });
    }

    @RequestMapping("user/order/pay")
    public String pay(HttpServletRequest request,
                      Model model) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            Order curOrder = (Order) request.getSession().getAttribute("curUserOrder");
            if (curOrder == null) {
                return "redirect:/";
            }

            currentUser.addOrders(curOrder);
            userService.saveOrUpdate(currentUser);

            // Update current user data
            userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

            request.getSession().setAttribute("curUserOrder", null);
            if (isSinglePage) {
                return "redirect:/";
            }
            return "redirect:/user/clearcart";

        });
    }
}
