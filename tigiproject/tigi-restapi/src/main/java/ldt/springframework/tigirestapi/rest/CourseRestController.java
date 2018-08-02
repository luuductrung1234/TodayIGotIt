package ldt.springframework.tigirestapi.rest;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.services.CourseService;
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
public class CourseRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseService courseService;


    // =======================================
    // =             REST Method             =
    // =======================================

    @GetMapping("/courses")
    public List<Course> getAllCourse(){
        return (List<Course>) courseService.listAll();
    }
}
