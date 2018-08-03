package ldt.springframework.springmvc.controller;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseFormConverter courseFormConverter;


    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String msg = "";
    private boolean failure;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @RequestMapping("/courses")
    public String listProducts(Model model){
        if(!failure){
            model.addAttribute("message", null);
        }else{
            model.addAttribute("message", msg);
        }
        model.addAttribute("courses", courseService.listAll());
        failure = false;

        return "view/course/courses";
    }

    @RequestMapping("/course/show/{id}")
    public String getCourseById(@PathVariable Integer id, Model model){
        model.addAttribute("course", courseService.getById(id));

        return "view/course/course";
    }

    @RequestMapping("/course/new")
    public String newCourse(Model model){
        model.addAttribute("courseForm", new CourseForm());

        return "view/course/courseForm";
    }

    @RequestMapping("/course/edit/{id}")
    public String editCourse(@PathVariable Integer id, Model model){
        model.addAttribute("courseForm", courseFormConverter.revert(courseService.getById((id))));

        return "view/course/courseForm";
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public String saveOrUpdateCourse(@Valid CourseForm course,
                                     BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "view/course/courseForm";
        }

        try{
            Course savedCourse = courseService.saveOrUpdateCourseForm(course);
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
}
