package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ldt.springframework.tigibusiness.commands.CourseForm;
import ldt.springframework.tigibusiness.commands.RateForm;
import ldt.springframework.tigibusiness.commands.UserForm;
import ldt.springframework.tigibusiness.commands.converters.CourseFormConverter;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseDetails;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.CourseOwnerService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.UserService;
import ldt.springframework.tigirestapi.exception.course.CourseNotFoundException;
import ldt.springframework.tigirestapi.exception.course.CourseSaveFailException;
import ldt.springframework.tigirestapi.exception.user.UserCourseNotOwnedException;
import ldt.springframework.tigirestapi.exception.user.UserNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    UserService userService;

    @Autowired
    CourseOwnerService courseOwnerService;

    @Autowired
    CourseFormConverter courseFormConverter;

    @Autowired
    UserFormConverter userFormConverter;



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


    @ApiOperation(value = "Get Course Resources", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 401, message = "You are not authorized to do  that"),
            @ApiResponse(code = 404, message = "Course not found"),
            @ApiResponse(code = 400, message = "Course has not owned yet"),
            @ApiResponse(code = 500, message = "Fail to get resource"),
    })
    @GetMapping(value = "/course/{id}/resources", produces = "application/json")
    public List<CourseDetails> getCourseResources(@PathVariable Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = userService.findByUserName(userDetails.getUsername());

        if(curUser == null)
            throw new UserNotAvailableException();

        if(!userService.checkCourseOwned(curUser, id)){
            throw new UserCourseNotOwnedException();
        }

        Course course = courseService.getById(id);
        if(course == null)
            throw new CourseNotFoundException(id.toString());

        return course.getCourseDetails();
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

        // TODO : Check increase view count
        course.setViewCount(course.getViewCount() + 1);
        course = courseService.saveOrUpdate(course);
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

    @ApiOperation(value = "Show all courses (Pagination)", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    })
    @GetMapping(value = "/courses/page/{pnum}/size/{snum}", produces = "application/json")
    public List<CourseForm> getAllCourseWithPagination(@PathVariable("pnum") Integer pnum,
                                                        @PathVariable("snum") Integer snum) {
        List<CourseForm> courseForms = new ArrayList<>();
        for (Course course :
                courseService.listAll(PageRequest.of(pnum, snum,
                        Sort.by(new Sort.Order(Sort.Direction.DESC, "buyCount"),
                                new Sort.Order(Sort.Direction.DESC, "viewCount"))))) {
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

    @ApiOperation(value = "Search course by description", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    })
    @GetMapping(value = "/course/find/{desc}/page/{pnum}/size/{snum}", produces = "application/json")
    public List<CourseForm> getCourseByDescWithPagination(@PathVariable("desc") String desc,
                                                          @PathVariable("pnum") Integer pnum,
                                                          @PathVariable("snum") Integer snum) {
        List<CourseForm> listCourse = new ArrayList<>();
        for (Course course :
                courseService.findByDesc(desc,
                        PageRequest.of(pnum, snum,
                                Sort.by(new Sort.Order(Sort.Direction.DESC, "buyCount"),
                                        new Sort.Order(Sort.Direction.DESC, "viewCount"))))) {
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

    @ApiOperation(value = "Show rate full statistic of specific course", response = Float.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Course not found"),
    })
    @GetMapping(value = "/course/{id}/rate/full", produces = "application/json")
    public RateForm showCourseRateFull(@PathVariable("id") Integer courseId) {

        Course course = courseService.getById(courseId);

        if (course != null) {
            RateForm rateForm = courseOwnerService.getCourseRateFull(course);

            return rateForm;
        } else {
            throw new CourseNotFoundException(courseId.toString());
        }
    }

    @ApiOperation(value = "Show Instructor of specific course", response = Float.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved resource"),
            @ApiResponse(code = 404, message = "Course not found"),
    })
    @GetMapping(value = "/course/{id}/instructor")
    public UserForm showInstructorForCourse(@PathVariable("id") Integer courseId) {
        Course course = courseService.getById(courseId);

        if (course != null) {
            User user = courseOwnerService.findInstructor(course);

            return userFormConverter.revertToFewInfo(user);
        } else {
            throw new CourseNotFoundException(courseId.toString());
        }
    }


}
