package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.*;
import ldt.springframework.springmvc.services.CartService;
import ldt.springframework.springmvc.services.CourseService;
import ldt.springframework.springmvc.services.UserService;
import ldt.springframework.springmvc.sercurity.TiGiAuthService;
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
public class CartController {

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
    private TiGiAuthService tiGiAuthService;


    // =======================================
    // =           Request Mapping           =
    // =======================================

    @RequestMapping("/user/addtocart/{id}")
    public String addToCart(@PathVariable("id") Integer courseId,
                            @RequestParam("currentPath") String currentPath,
                            HttpServletRequest request,
                            Model model){

        return tiGiAuthService.sessionCheckLogin( "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            Course course = courseService.getById(courseId);
            cartService.addToCart(currentUser, course);
            userService.saveOrUpdate(currentUser);

            // Update current user data
            userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

            return "redirect:" + currentPath;
        });
    }

    @RequestMapping("/user/removefromcart/{id}")
    public String removeFromCart(@PathVariable("id") Integer courseId,
                            @RequestParam("currentPath") String currentPath,
                            HttpServletRequest request,
                            Model model){

        return tiGiAuthService.sessionCheckLogin( "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            cartService.removeFromCart(currentUser, courseId);
            userService.saveOrUpdate(currentUser);

            // Update current user data
            userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

            return "redirect:" + currentPath;
        });
    }

    @RequestMapping("/user/showcart")
    public String showCart(HttpServletRequest request, Model model){

        return tiGiAuthService.sessionCheckLogin( "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            model.addAttribute("cart", currentUser.getCart());
            model.addAttribute("totalPrice", cartService.calculateTotalPrice(currentUser.getCart()));

            return "view/cart/showCart";
        });
    }

    @RequestMapping("/user/clearcart")
    public String clearCart(HttpServletRequest request, Model model){

        return tiGiAuthService.sessionCheckLogin( "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            currentUser.removeCart(currentUser.getCart());
            userService.saveOrUpdate(currentUser);

            // Update current user data
            currentUser = userService.getById(currentUser.getId());
            currentUser.setCart(new Cart());
            userService.saveOrUpdate(currentUser);
            userService.updateCurrentUserDataToSession(request, cartService, currentUser.getId());

            return "redirect:/";
        });
    }
}
