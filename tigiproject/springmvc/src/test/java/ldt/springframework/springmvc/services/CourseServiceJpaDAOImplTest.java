package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( classes = JpaIntegrationConfig.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class CourseServiceJpaDAOImplTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseService courseService;


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListCourses() throws Exception{
        List<Course> courses = (List<Course>) courseService.listAll();

        assert courses.size() == 6;
    }

    @Test
    public void testGetCourseByid() throws Exception{
        Course course = courseService.getById(1);

        assert course.getId() == 1;
        assert course.getDescription().equals("Course 1");
        assert course.getPrice().equals(new BigDecimal("12.99"));
    }

    @Test
    public void testCreateNewCourse() throws Exception{
        Course newCourse = new Course(null, "Course 7", new BigDecimal("7.89"), "example.com/product7");
        Course savedCourse = courseService.saveOrUpdate(newCourse);

        // test generated id
        assert savedCourse.getId() == 7;

        // test total courses after add
        List<Course> courses = (List<Course>) courseService.listAll();
        assert courses.size() == 7;
    }

    @Test
    public void testEditCourse() throws Exception{
        Course uptCourse = new Course(2, "Course 2 (Updated)", new BigDecimal("1.11"), "example.com/product2new");
        uptCourse.setVersion(0);
        Course savedCourse = courseService.saveOrUpdate(uptCourse);

        assert savedCourse.getId() == 2;
        assert savedCourse.getDescription().equals("Course 2 (Updated)");
        assert savedCourse.getPrice().equals(new BigDecimal("1.11"));
        assert savedCourse.getImageUrl().equals("example.com/product2new");

        // test again
        Course course = courseService.getById(2);
        assert course.getId() == 2;
        assert course.getDescription().equals("Course 2 (Updated)");
        assert course.getPrice().equals(new BigDecimal("1.11"));
        assert course.getImageUrl().equals("example.com/product2new");
    }

    @Test
    public void testDeleteCourse() throws Exception{

        Course newCourse = new Course(null, "Course 7", new BigDecimal("7.89"), "example.com/product7");
        Course savedCourse = courseService.saveOrUpdate(newCourse);
        // generated new course
        assert savedCourse.getId() == 7;


        // delete the new course above
        courseService.delete(7);
        List<Course> courses = (List<Course>) courseService.listAll();
        assert courses.size() == 6;
    }
}