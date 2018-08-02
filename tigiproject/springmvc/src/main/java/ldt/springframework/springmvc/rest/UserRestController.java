package ldt.springframework.springmvc.rest;

import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/1/18
 */


@RestController
public class UserRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    UserService userService;

    // =======================================
    // =            REST Methods             =
    // =======================================

    @GetMapping("/api/users")
    public List<User> getAllUser(){
        return null;
    }
}
