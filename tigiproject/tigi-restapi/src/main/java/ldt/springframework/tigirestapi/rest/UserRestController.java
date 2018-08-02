package ldt.springframework.tigirestapi.rest;

import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.sercurity.TiGiAuthService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/1/18
 */


@RestController
@RequestMapping("/api")
public class UserRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private TiGiAuthService tiGiAuthService;


    // =======================================
    // =            REST Methods             =
    // =======================================

    @GetMapping(value = "/users")
    public List<User> getAllUser(){
        return (List<User>) userService.listAll();
    }

    @GetMapping(value = "/user/find/{username}")
    public Customer getUserByUsername(@PathVariable String username){
        return userService.findByUserName(username).getCustomer();
    }

    @GetMapping(value = "/users/count")
    public int countUsers(){
        return userService.listAll().size();
    }


    @GetMapping(value = "/user/show")
    public User showUser(){
        return tiGiAuthService.sessionCheckLogin(null, () ->{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.findByUserName(userDetails.getUsername());
        });
    }


}
