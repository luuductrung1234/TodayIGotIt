package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.services.CourseOwnerService;
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
@Api(value = "Course API", description = "Operation pertaining to Course")
public class CourseRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseService courseService;

    @Autowired
    CourseOwnerService courseOwnerService;

    @Autowired
    CourseFormConverter courseFormConverter;



    // =======================================
    // =           Auth REST Method          =
    // =======================================

    @ApiOperation(value = "Create new course", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 403, message = "You don't have right to do  that"),
            @ApiResponse(code = 400, message = "Password not match or invalid input information"),
            @ApiResponse(code = 500, message = "Create fail"),
    })
    @PostMapping(value = "/course/new", consumes = "application/json")
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

    @ApiOperation(value = "Show information of specific course by id", response = CourseForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Course not found"),
    })
    @GetMapping(value = "/course/info/{id}", produces = "application/json")
    public CourseForm getCourseById(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        if (course == null) {
            throw new CourseNotFoundException(id.toString());
        }

        return courseFormConverter.revert(course);
    }


    @ApiOperation(value = "Show all courses", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    })
    @GetMapping(value = "/courses", produces = "application/json")
    public List<CourseForm> getAllCourse() {
        List<CourseForm> courseForms = new ArrayList<>();
        for (Course course :
                (List<Course>) courseService.listAll()) {
            courseForms.add(courseFormConverter.revert(course));
        }

        return courseForms;
    }


    @ApiOperation(value = "Search course by description", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    })
    @GetMapping(value = "/course/find/{desc}", produces = "application/json")
    public List<CourseForm> getCourseByDesc(@PathVariable String desc) {
        List<CourseForm> listCourse = new ArrayList<>();
        for (Course course :
                courseService.findByDesc(desc)) {
            listCourse.add(courseFormConverter.revert(course));
        }

        return listCourse;
    }

    @ApiOperation(value = "Show review of specific course", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Course not found"),
    })
    @GetMapping(value = "/course/{id}/review", produces = "application/json")
    public List<CourseOwner> showCourseReview(@PathVariable("id") Integer courseId) {

        Course course = courseService.getById(courseId);

        if (course != null) {
            List<CourseOwner> listReview = courseOwnerService.findReviewByCourse(course);

            return listReview;
        } else {
            throw new CourseNotFoundException(courseId.toString());
        }
    }

    @ApiOperation(value = "Show rate of specific course", response = Float.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Course not found"),
    })
    @GetMapping(value = "/course/{id}/rate")
    public Float showCourseRate(@PathVariable("id") Integer courseId) {

        Course course = courseService.getById(courseId);

        if (course != null) {
            Float rateAvg = courseOwnerService.getCourseRateAvg(course);

            return rateAvg;
        } else {
            throw new CourseNotFoundException(courseId.toString());
        }
    }
}
