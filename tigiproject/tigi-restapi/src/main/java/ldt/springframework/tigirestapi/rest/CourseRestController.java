package ldt.springframework.tigirestapi.rest;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseService courseService;


    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @GetMapping(value = "/course/show/{id}")
    public Course getCourseById(@PathVariable Integer id){
        return courseService.getById(id);
    }


    // =======================================
    // =         Non-Auth REST Method        =
    // =======================================

    @GetMapping(value = "/courses") public List<Course> getAllCourse(){
        return (List<Course>) courseService.listAll();
    }

    @GetMapping(value = "/course/find/{desc}")
    public List<Course> getCourseByDesc(@PathVariable String desc){
        return courseService.findByDesc(desc);
    }

    @PostMapping(value = "/course/create")
    public User createNewUser(@RequestParam Course course){
        return null;
    }
}
