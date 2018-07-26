package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.services.CourseService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */


@Controller
public class IndexController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseService courseService;



    // =======================================
    // =           Request Mapping            =
    // =======================================

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
        List<Course> courses = (List<Course>) courseService.listAll();

        model.addAttribute("currentUser", request.getSession().getAttribute("curUser"));
        model.addAttribute("courses", courses);

        return "index";
    }

}
