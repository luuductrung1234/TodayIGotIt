package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.commands.UserForm;
import ldt.springframework.springmvc.commands.converters.UserFormConverter;
import ldt.springframework.springmvc.controller.security.CurrentUserSecurity;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CartService;
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

    @Autowired
    CartService cartService;

    @Autowired
    UserFormConverter userFormConverter;

    @Autowired
    CurrentUserSecurity currentUserSecurity;


    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String msg = "";
    private boolean failure;


    // =======================================
    // =             GET Methods             =
    // =======================================

    @RequestMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Model model) {

        User loginUser = userService.login(username, password);
        if (loginUser != null) {
            userService.updateLoginUserDataToSession(request, cartService, loginUser);
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/user/logout")
    public String logout(WebRequest request, SessionStatus status) {
        userService.updateLogoutUserToSession(request, status);

        return "redirect:/";
    }


    @RequestMapping("/user/show")
    public String showUserDetails(HttpServletRequest request, Model model) {
        if (failure) {
            model.addAttribute("message", msg);
            this.stateReset();
        }

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            model.addAttribute("currentUser", currentUser);
            return "view/user/dashboard";
        });
    }

    @RequestMapping("/user/new")
    public String newUser(Model model) {

        model.addAttribute("user", new UserForm());

        return "view/user/userForm";
    }

    @RequestMapping("/user/edit/info")
    public String editUserInfo(HttpServletRequest request, Model model) {
        // TODO: Upgrade to patch update

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            model.addAttribute("currentUser", userFormConverter.revert(currentUser));
            return "view/user/updateInfoForm";
        });
    }

    @RequestMapping("/user/edit/username")
    public String editUserUsername(HttpServletRequest request, Model model) {
        // TODO: Upgrade to patch update

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            model.addAttribute("currentUser", userFormConverter.revert(currentUser));
            return "view/user/updateUsernameForm";
        });
    }

    @RequestMapping("/user/edit/password")
    public String editUserPassword(HttpServletRequest request, Model model) {
        // TODO: Upgrade to patch update

        if (failure) {
            model.addAttribute("message", msg);
            this.stateReset();
        }

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            model.addAttribute("currentUser", userFormConverter.revert(currentUser));
            return "view/user/updatePasswordForm";
        });
    }

    @RequestMapping("/user/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id, Model model) {
        try {
            userService.delete(id);
            stateReset();
        } catch (Exception ex) {
            msg = "Delete fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/user/logout";
    }



    // =======================================
    // =            POST Methods             =
    // =======================================

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveOrUpdateUser(UserForm userForm) {
        try {
            userService.saveOrUpdateUserForm(userForm);
        } catch (Exception ex) {
            msg = "Save fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/user/show";
    }

    @RequestMapping(value = "/user/uptPassword", method = RequestMethod.POST)
    public String updatePassword(UserForm userForm, HttpServletRequest request) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            if (!userForm.getPasswordTextConf().equals(userForm.getPasswordText())) {
                msg = "Confirm Password Not Match!";
                failure = true;
                return "redirect:/user/edit/password/";
            }

            User currentUser = (User) request.getSession().getAttribute("curUser");
            userForm.setUserCart(currentUser.getCart());
            saveOrUpdateUser(userForm);

            return "redirect:/user/logout";
        });
    }

    @RequestMapping(value = "/user/uptUsername", method = RequestMethod.POST)
    public String updateUsername(UserForm userForm,
                                 HttpServletRequest request) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            User currentUser = (User) request.getSession().getAttribute("curUser");
            userForm.setUserCart(currentUser.getCart());
            saveOrUpdateUser(userForm);

            return "redirect:/user/logout";
        });
    }

    @RequestMapping(value = "/user/uptInfo", method = RequestMethod.POST)
    public String updateInfo(UserForm userForm,
                             HttpServletRequest request) {

        return currentUserSecurity.sessionCheckLogin(request, "redirect:/", () -> {
            try {
                User currentUser = (User) request.getSession().getAttribute("curUser");
                userForm.setUserCart(currentUser.getCart());
                customerService.saveOrUpdateUserForm(userForm);
            } catch (Exception ex) {
                msg = "Save fail! Something went wrong!";
                failure = true;
            }

            userService.updateCurrentUserDataToSession(request, cartService,userForm.getUserId());

            return "redirect:/user/show";
        });
    }



    // =======================================
    // =          Business Methods           =
    // =======================================

    private void stateReset() {
        this.msg = "";
        this.failure = false;
    }
}