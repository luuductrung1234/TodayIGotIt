package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */


@Controller
public class CourseController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    private CourseService courseService;


    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String msg = "";
    private boolean failure;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @RequestMapping("/courses")
    public String listProducts(HttpServletRequest request, Model model){
        if(!failure){
            model.addAttribute("message", null);
        }else{
            model.addAttribute("message", msg);
        }
        model.addAttribute("courses", courseService.listAll());
        model.addAttribute("currentUser", request.getSession().getAttribute("curUser"));
        failure = false;

        return "view/course/courses";
    }

    @RequestMapping("/course/{id}")
    public String getCourseById(@PathVariable Integer id, HttpServletRequest request, Model model){
        model.addAttribute("course", courseService.getById(id));
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/course/course";
    }

    @RequestMapping("/course/new")
    public String newCourse(HttpServletRequest request, Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/course/courseForm";
    }

    @RequestMapping("/course/edit/{id}")
    public String editCourse(@PathVariable Integer id, HttpServletRequest request, Model model){
        model.addAttribute("course", courseService.getById((id)));
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/course/courseForm";
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public String saveOrUpdateCourse(Course course){
        try{
            Course savedCourse = courseService.saveOrUpdate(course);
            failure = false;

            return "redirect:course/" + savedCourse.getId();
        }catch (Exception ex){
            msg = "Save fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/courses";
    }


    @RequestMapping("course/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model){
        try{
            courseService.delete(id);
            failure = false;
        }catch (Exception ex){
            msg = "Delete fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/courses";
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @Autowired
    public void setCourseService(CourseService courseService){
        this.courseService = courseService;
    }
}
