package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CustomerService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/19/18
 */


@Controller
public class UserController {
    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;


    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String msg = "";
    private boolean failure;


    // =======================================
    // =           Request Mappin            =
    // =======================================


    @RequestMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model){

        User loginUser = userService.login(username, password);
        if(loginUser != null){
            request.getSession().setAttribute("curUser", loginUser);
        }


        return "redirect:/";
    }

    @RequestMapping(value = "/user/logout")
    public String logout(WebRequest request, SessionStatus status){
        status.setComplete();
        request.removeAttribute("curUser", WebRequest.SCOPE_SESSION);

        return "redirect:/";
    }


    @RequestMapping("/user/show")
    public String showUserDetails(HttpServletRequest request, Model model){
        if(failure){
            model.addAttribute("message", msg);
            this.stateReset();
        }

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser != null){
            model.addAttribute("currentUser", currentUser);
            return "view/user/dashboard";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping("/user/new")
    public String newUser(HttpServletRequest request, Model model){

        User newUser = new User();
        newUser.setCustomer(new Customer());
        model.addAttribute("user", newUser);
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/user/userform";
    }

    @RequestMapping("/user/edit/info/{id}")
    public String editUserInfo(@PathVariable Integer id, HttpServletRequest request, Model model){
        // TODO: Upgrade to patch update

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }
        model.addAttribute("currentUser", currentUser);

        return "view/user/updateInfoForm";
    }

    @RequestMapping("/user/edit/username/{id}")
    public String editUserUsername(@PathVariable Integer id, HttpServletRequest request, Model model){
        // TODO: Upgrade to patch update

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }
        model.addAttribute("currentUser", currentUser);

        return "view/user/updateUsernameForm";
    }

    @RequestMapping("/user/edit/password/{id}")
    public String editUserPassword(@PathVariable Integer id, HttpServletRequest request, Model model){
        // TODO: Upgrade to patch update

        if(failure){
            model.addAttribute("message", msg);
            this.stateReset();
        }

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }
        model.addAttribute("currentUser", currentUser);
//
        return "view/user/updatePasswordForm";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveOrUpdateUser(User user){
        try{
            userService.saveOrUpdate(user);
        }catch (Exception ex){
            msg = "Save fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/user/show";
    }

    @RequestMapping(value = "/user/uptPassword", method = RequestMethod.POST)
    public String updatePassword(@RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("password") String password,
                                 HttpServletRequest request){
        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }

        if(!confirmPassword.equals(password))
        {
            msg = "Confirm Password Not Match!";
            failure = true;
            return "redirect:/user/edit/password/" + currentUser.getId();
        }
        currentUser.setPassword(password);
        saveOrUpdateUser(currentUser);

        return "redirect:/user/logout";
    }

    @RequestMapping(value = "/user/uptUsername", method = RequestMethod.POST)
    public String updateUsername(@RequestParam("username") String username,
                                 HttpServletRequest request){
        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }

        currentUser.setUsername(username);

        return saveOrUpdateUser(currentUser);
    }

    @RequestMapping(value = "/user/uptInfo", method = RequestMethod.POST)
    public String updateInfo(@RequestParam("firstname") String firstName,
                             @RequestParam("lastname") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("phonenumber") String phoneNumber,
                             @RequestParam("billAddressLine1") String billAddressLine1,
                             @RequestParam("billAddressLine2") String billAddressLine2,
                             @RequestParam("billCity") String billCity,
                             @RequestParam("billState") String billState,
                             @RequestParam("billZipCode") String billZipCode,
                             @RequestParam("shipAddressLine1") String shipAddressLine1,
                             @RequestParam("shipAddressLine2") String shipAddressLine2,
                             @RequestParam("shipCity") String shipCity,
                             @RequestParam("shipState") String shipState,
                             @RequestParam("shipZipCode") String shipZipCode,
                             HttpServletRequest request){

        User currentUser = (User) request.getSession().getAttribute("curUser");
        if(currentUser == null)
        {
            return "redirect:/";
        }

        currentUser.getCustomer().setFirstName(firstName);
        currentUser.getCustomer().setLastName(lastName);
        currentUser.getCustomer().setEmail(email);
        currentUser.getCustomer().setPhoneNumber(phoneNumber);
        currentUser.getCustomer().getBillingAddress().setAddressLine1(billAddressLine1);
        currentUser.getCustomer().getBillingAddress().setAddressLine2(billAddressLine2);
        currentUser.getCustomer().getBillingAddress().setCity(billCity);
        currentUser.getCustomer().getBillingAddress().setState(billState);
        currentUser.getCustomer().getBillingAddress().setZipCode(billZipCode);
        currentUser.getCustomer().getShippingAddress().setAddressLine1(shipAddressLine1);
        currentUser.getCustomer().getShippingAddress().setAddressLine2(shipAddressLine2);
        currentUser.getCustomer().getShippingAddress().setCity(shipCity);
        currentUser.getCustomer().getShippingAddress().setState(shipState);
        currentUser.getCustomer().getShippingAddress().setZipCode(shipZipCode);

        try{
            customerService.saveOrUpdate(currentUser.getCustomer());
        }catch (Exception ex){
            msg = "Save fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/user/show";
    }

    @RequestMapping("/user/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id, Model model){
        try{
            userService.delete(id);
            stateReset();
        }catch (Exception ex){
            msg = "Delete fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/user/logout";
    }

    @RequestMapping("/user/addtocart/{id}")
    public String addToCart(@PathVariable Integer productId,
                            @RequestParam("currentPath") String currentPath,
                            Model model){

        // TODO: implement User Add To Cart

        return currentPath;
    }



    private void stateReset(){
        this.msg = "";
        this.failure = false;
    }
}
