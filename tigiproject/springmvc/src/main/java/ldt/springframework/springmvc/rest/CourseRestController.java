package ldt.springframework.springmvc.rest;

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
    private CourseService courseService;


    // =======================================
    // =            REST Methods             =
    // =======================================

    @GetMapping("/api/courses")
    public List<Course> getAllCourses(){
        return (List<Course>) courseService.listAll();
    }
}
