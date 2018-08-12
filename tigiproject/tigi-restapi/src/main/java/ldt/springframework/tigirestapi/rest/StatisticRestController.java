package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import ldt.springframework.tigibusiness.commands.converters.UserFormConverter;
import ldt.springframework.tigibusiness.commands.statistic.*;
import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/11/18
 */



@RestController
@RequestMapping("/api")
@Api(value = "Statistic API", description = "Perform Visualization Data")
public class StatisticRestController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    CourseService courseService;

    @Autowired
    CourseOwnerService courseOwnerService;

    @Autowired
    OrderService orderService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserFormConverter userFormConverter;

    @Autowired
    UserService userService;


    // =======================================
    // =            REST Methods             =
    // =======================================

    @GetMapping(value = "/statistic/course/buy")
    public List<CourseBuy> totalCourseBuy(){
        List<Course> courses = (List<Course>) courseService.listAll();
        List<CourseBuy> courseBuys = new ArrayList<>();
        for (Course course:
             courses) {
            courseBuys.add(new CourseBuy(course.getDescription(), course.getBuyCount()));
        }

        return courseSortByBuy(courseBuys);
    }

    @GetMapping(value = "/statistic/course/rate")
    public List<CourseRate> totalCourseRate(){
        List<Course> courses = (List<Course>) courseService.listAll();
        List<CourseRate> courseRates = new ArrayList<>();
        for (Course course : courses){
            Float rateAvg = courseOwnerService.getCourseRateAvg(course);
            if(rateAvg == null || rateAvg == Float.NaN)
                rateAvg = 0f;
            courseRates.add(new CourseRate(course.getDescription(), rateAvg));
        }

        return courseSortByRate(courseRates);
    }

    @GetMapping(value = "/statistic/course/most/buy")
    public List<CourseBuy> totalCourseMostBuy(){
        List<Course> courses = (List<Course>) courseService.listAll();
        List<CourseBuy> courseBuys = new ArrayList<>();
        for (Course course:
                courses) {
            courseBuys.add(new CourseBuy(course.getDescription(), course.getBuyCount()));
        }

        return courseSortByBuy(courseBuys).subList(0, 10);
    }

    @GetMapping(value = "/statistic/course/most/rate")
    public List<CourseRate> totalCourseMostRate(){
        List<Course> courses = (List<Course>) courseService.listAll();
        List<CourseRate> courseRates = new ArrayList<>();
        for (Course course : courses){
            Float rateAvg = courseOwnerService.getCourseRateAvg(course);
            if(rateAvg == null || rateAvg == Float.NaN)
                rateAvg = 0f;
            courseRates.add(new CourseRate(course.getDescription(), rateAvg));
        }

        return courseSortByRate(courseRates).subList(0, 10);
    }


    @GetMapping(value = "/statistic/receipt/day/{modify}")
    public List<ReceiptByDay> totalReceiptByDay(@PathVariable("modify") int modify){
        return orderService.receiptByDay(modify);
    }

    @GetMapping(value = "/statistic/receipt/month/{modify}")
    public List<ReceiptByMonth> totalReceiptByMonth(@PathVariable("modify") int modify){
        return orderService.receiptByMonth(modify);
    }

    @GetMapping(value = "/statistic/receipt/year/{modify}")
    public List<ReceiptByYear> totalReceiptByYear(@PathVariable("modify") int modify){
        return orderService.receiptByYear(modify);
    }


    @GetMapping(value = "/statistic/instructor/most/buy")
    public List<InstructorBuy> instructorMostBuy(){
        Role role = roleService.findFirstByType(RoleType.TEACHER);
        List<User> instructors = userService.findAllByRolesContaining(role);

        List<InstructorBuy> instructorBuys = new ArrayList<>();
        for (User instructor:
             instructors) {

            Integer buyCount = 0;
            for (CourseOwner courseOwner :
                 instructor.getCourseOwners()) {
                buyCount += courseOwner.getCourse().getBuyCount();
            }

            instructorBuys.add(new InstructorBuy(buyCount, userFormConverter.revertToFewInfo(instructor)));
        }

        // TODO : sort

        return instructorBuys;
    }


    @GetMapping(value = "/statistic/student/most/buy")
    public List<StudentBuy> studentMostBuy(){
        Role role = roleService.findFirstByType(RoleType.STUDENT);
        List<User> students = userService.findAllByRolesContaining(role);

        List<StudentBuy> studentBuys = new ArrayList<>();
        for (User student:
                students) {

            Integer buyCount = 0;
            for (Order order :
                    student.getOrders()) {
                for (OrderDetails orderDetails:
                     order.getOrderDetails()) {
                    buyCount += orderDetails.getQuantity();
                }
            }

            studentBuys.add(new StudentBuy(buyCount, userFormConverter.revertToFewInfo(student)));
        }

        // TODO : sort

        return studentBuys;
    }



    // =======================================
    // =          Business Methods           =
    // =======================================

    private List<CourseRate> courseSortByRate(List<CourseRate> courseRates){
        Collections.sort(courseRates, new Comparator() {
            public int compare(Object synchronizedListOne, Object synchronizedListTwo) {
//use instanceof to verify the references are indeed of the type in question
                return -((CourseRate)synchronizedListOne).getRate()
                        .compareTo(((CourseRate)synchronizedListTwo).getRate());
            }
        });

        return courseRates;
    }

    private List<CourseBuy> courseSortByBuy(List<CourseBuy> courseBuys){
        Collections.sort(courseBuys, new Comparator() {
            public int compare(Object synchronizedListOne, Object synchronizedListTwo) {
//use instanceof to verify the references are indeed of the type in question
                return -((CourseBuy)synchronizedListOne).getBuyCount()
                        .compareTo(((CourseBuy)synchronizedListTwo).getBuyCount());
            }
        });

        return courseBuys;
    }
}
