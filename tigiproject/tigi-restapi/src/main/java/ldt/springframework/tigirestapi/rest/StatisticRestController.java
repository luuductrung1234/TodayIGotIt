package ldt.springframework.tigirestapi.rest;

import io.swagger.annotations.Api;
import ldt.springframework.tigibusiness.commands.statistic.CourseBuy;
import ldt.springframework.tigibusiness.commands.statistic.CourseRate;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.CourseOwner;
import ldt.springframework.tigibusiness.services.CourseOwnerService;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
