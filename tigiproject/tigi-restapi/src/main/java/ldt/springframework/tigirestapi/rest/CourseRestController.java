package ldt.springframework.tigirestapi.rest;

import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    CourseFormConverter courseFormConverter;


    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @GetMapping(value = "/course/info/{id}")
    public CourseForm getCourseById(@PathVariable Integer id){
        return courseFormConverter.revert(courseService.getById(id));
    }

    @PostMapping(value = "/course/new")
    public CourseForm createNewCourse(@RequestBody CourseForm courseForm){
        try{
            Course savedCourse = courseService.saveOrUpdateCourseForm(courseForm);

            return courseFormConverter.revert(savedCourse);
        }catch (Exception ex){
            ex.getStackTrace();
        }

        return null;
    }


    // =======================================
    // =         Non-Auth REST Method        =
    // =======================================

    @GetMapping(value = "/courses")
    public List<CourseForm> getAllCourse(){
        List<CourseForm> courseForms = new ArrayList<>();
        for (Course course:
             (List<Course>) courseService.listAll()) {
            courseForms.add(courseFormConverter.revert(course));
        }

        return  courseForms;
    }

    @GetMapping(value = "/course/find/{desc}")
    public List<CourseForm> getCourseByDesc(@PathVariable String desc){
        List<CourseForm> courseForms = new ArrayList<>();
        for (Course course:
                courseService.findByDesc(desc)) {
            courseForms.add(courseFormConverter.revert(course));
        }
        return courseForms;
    }
}
