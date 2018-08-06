package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.exception.course.CourseSaveFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
@Api(value = "Course API", description = "Operations pertaining to Course and Course Information")
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

    @PostMapping(value = "/course/new")
    public ResponseEntity createNewCourse(@RequestBody CourseForm courseForm) {
        try {
            Course savedCourse = courseService.saveOrUpdateCourseForm(courseForm);

            if (savedCourse == null)
                throw new CourseSaveFailException();

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/course/info/" + savedCourse.getId())
                    .build()
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CourseSaveFailException();
        }
    }


    // =======================================
    // =         Non-Auth REST Method        =
    // =======================================

    @GetMapping(value = "/course/info/{id}")
    public CourseForm getCourseById(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        if (course == null) {
            throw new CourseNotFoundException(id.toString());
        }

        return courseFormConverter.revert(course);
    }

    //@CrossOrigin(origins = "*")
    @GetMapping(value = "/courses")
    public List<CourseForm> getAllCourse() {
        List<CourseForm> courseForms = new ArrayList<>();
        for (Course course :
                (List<Course>) courseService.listAll()) {
            courseForms.add(courseFormConverter.revert(course));
        }

        return courseForms;
    }

    @GetMapping(value = "/course/find/{desc}")
    public List<CourseForm> getCourseByDesc(@PathVariable String desc) {
        List<CourseForm> listCourse = new ArrayList<>();
        for (Course course :
                courseService.findByDesc(desc)) {
            listCourse.add(courseFormConverter.revert(course));
        }

        if (listCourse.isEmpty()) {
            throw new CourseNotFoundException(desc);
        }

        return listCourse;
    }
}
