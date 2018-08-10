package ldt.springframework.tigirestapi.bootstrap;

import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.OrderStatus;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.enums.ResourceType;
import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.LearnTrackingService;
import ldt.springframework.tigibusiness.services.RoleService;
import ldt.springframework.tigibusiness.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 * ---
 *
 * This Spring Component will run every ContextRefreshEvent occur
 * to init some default database's record
 *
 * This bean can apply for both JPA service and Mapping service
 */


@Component
public class SpringDataBaseBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LearnTrackingService learnTrackingService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadCourse();
        this.loadUser();
        this.loadCurrentCart();
        this.loadOrderHistory();
        this.loadRoles();
        this.loadOwnedCourse();
        this.assignUsersToDefaultRole();
    }

    private void loadCourse() {
        Course course1 = new Course();
        course1.setDescription("C# Fundamentals");
        course1.setPrice(new BigDecimal("12.99"));
        course1.setImageUrl("static/images/img1.png");
        course1.setMediaPath("static/videos/tut1.mp4");
        course1.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course1.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut2.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut3.mp4"));
        course1.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut4.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut5.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign2.html"));
        course1.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut6.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign3.html"));
        course1.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course1.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course1);


        Course course2 = new Course();
        course2.setDescription("Java Master Class");
        course2.setPrice(new BigDecimal("9.99"));
        course2.setImageUrl("static/images/img2.jpg");
        course2.setMediaPath("static/videos/tut2.mp4");
        course2.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Java Components"))
                .addCourseDetails(new CourseDetails(3, "Details"))
                .addCourseDetails(new CourseDetails(4, "Build First App"))
                .addCourseDetails(new CourseDetails(5, "Exception Handling"));
        course2.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course2.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course2.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course2.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course2.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course2);

        Course course3 = new Course();
        course3.setDescription("Javascript For Beginner");
        course3.setPrice(new BigDecimal("10.09"));
        course3.setImageUrl("static/images/img3.png");
        course3.setMediaPath("static/videos/tut3.mp4");
        course3.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "Type Coercion"))
                .addCourseDetails(new CourseDetails(5, "Closure"));
        course3.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course3.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course3.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course3.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course3.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course3);

        Course course4 = new Course();
        course4.setDescription("Front-End BootCamp");
        course4.setPrice(new BigDecimal("34.99"));
        course4.setImageUrl("static/images/img4.jpeg");
        course4.setMediaPath("static/videos/tut4.mp4");
        course4.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic HTML"))
                .addCourseDetails(new CourseDetails(3, "Basic CSS"))
                .addCourseDetails(new CourseDetails(4, "Basic Javascript"))
                .addCourseDetails(new CourseDetails(5, "Build First App"));
        course4.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course4.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course4.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course4.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course4.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course4);

        Course course5 = new Course();
        course5.setDescription("NodeJs Complete Course");
        course5.setPrice(new BigDecimal("14.08"));
        course5.setImageUrl("static/images/img5.jpg");
        course5.setMediaPath("static/videos/tut5.mp4");
        course5.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course5.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course5.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course5.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course5.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course5.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course5);

        Course course6 = new Course();
        course6.setDescription("Cloud Computing Basic");
        course6.setPrice(new BigDecimal("20.05"));
        course6.setImageUrl("static/images/img6.jpg");
        course6.setMediaPath("static/videos/tut6.mp4");
        course6.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course6.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course6.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course6.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course6.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course6.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course6);

        Course course7 = new Course();
        course7.setDescription("Master Spring Framework");
        course7.setPrice(new BigDecimal("19.99"));
        course7.setImageUrl("static/images/img7.png");
        course7.setMediaPath("static/videos/tut6.mp4");
        course7.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course7.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course7.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course7.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course7.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course7.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course7);

        Course course8 = new Course();
        course8.setDescription("Angular Complete Course");
        course8.setPrice(new BigDecimal("19.99"));
        course8.setImageUrl("static/images/img8.jpg");
        course8.setMediaPath("static/videos/tut6.mp4");
        course8.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course8.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course8.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course8.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course8.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course8.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course8);

        Course course9 = new Course();
        course9.setDescription("React and Redux");
        course9.setPrice(new BigDecimal("20.05"));
        course9.setImageUrl("static/images/img9.png");
        course9.setMediaPath("static/videos/tut6.mp4");
        course9.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course9.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course9.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course9.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course9.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course9.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course9);

        Course course10 = new Course();
        course10.setDescription("Vue For Beginner");
        course10.setPrice(new BigDecimal("10.50"));
        course10.setImageUrl("static/images/img10.png");
        course10.setMediaPath("static/videos/tut6.mp4");
        course10.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course10.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course10.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course10.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course10.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course10.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course10);

        Course course11 = new Course();
        course11.setDescription("Master Front-End Design with SASS");
        course11.setPrice(new BigDecimal("2.05"));
        course11.setImageUrl("static/images/img11.png");
        course11.setMediaPath("static/videos/tut6.mp4");
        course11.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course11.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course11.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course11.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course11.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course11.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course11);

        Course course12 = new Course();
        course12.setDescription("MySQL with Real Project");
        course12.setPrice(new BigDecimal("10.99"));
        course12.setImageUrl("static/images/img12.jpeg");
        course12.setMediaPath("static/videos/tut6.mp4");
        course12.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course12.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course12.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course12.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course12.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course12.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course12);

        Course course13 = new Course();
        course13.setDescription("SQL Server and Data Analysis");
        course13.setPrice(new BigDecimal("90.50"));
        course13.setImageUrl("static/images/img13.png");
        course13.setMediaPath("static/videos/tut6.mp4");
        course13.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course13.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course13.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course13.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course13.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course13.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course13);

        Course course14 = new Course();
        course14.setDescription("Algorithm First Class");
        course14.setPrice(new BigDecimal("199.99"));
        course14.setImageUrl("static/images/img14.png");
        course14.setMediaPath("static/videos/tut6.mp4");
        course14.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course14.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course14.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course14.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course14.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course14.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course14);

        Course course15 = new Course();
        course15.setDescription("Understanding Data Structure");
        course15.setPrice(new BigDecimal("9.99"));
        course15.setImageUrl("static/images/img15.png");
        course15.setMediaPath("static/videos/tut6.mp4");
        course15.addCourseDetails(new CourseDetails(1, "Introduction"))
                .addCourseDetails(new CourseDetails(2, "Basic Concept"))
                .addCourseDetails(new CourseDetails(3, "Types"))
                .addCourseDetails(new CourseDetails(4, "First Console App"))
                .addCourseDetails(new CourseDetails(5, "File Manipulation"));
        course15.getCourseDetails()
                .get(0)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course15.getCourseDetails()
                .get(1)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Assign 2", ResourceType.FILE, "static/files/assign.html"));
        course15.getCourseDetails()
                .get(2)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(1, "Assign 1", ResourceType.FILE, "static/files/assign.html"));
        course15.getCourseDetails()
                .get(3)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(2, "Video 2", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(3, "Video 3", ResourceType.VIDEO, "static/videos/tut1.mp4"))
                .addCourseResources(new CourseResource(4, "Video 4", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        course15.getCourseDetails()
                .get(4)
                .addCourseResources(new CourseResource(1, "Video 1", ResourceType.VIDEO, "static/videos/tut1.mp4"));
        courseService.saveOrUpdate(course15);

    }

    private void loadUser() {
        User user1 = new User();
        user1.setUsername("luuductrung1234");
        user1.setPassword("Trung1997");
        Customer customer1 = new Customer(null, "Trung", "Luu",
                "luuductrung@gmail.com", "012323774078",
                new Address("123 ABC p1", "", "HCM", "South", "700000"),
                new Address("111 CA ltd p1", "", "HCM", "South", "700000"));
        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);


        User user2 = new User();
        user2.setUsername("tranhanhtuan");
        user2.setPassword("123");
        Customer customer2 = new Customer(null, "Tuan", "Tran",
                "trananhtuan@gmail.com", "013242353248",
                new Address("456 VCD p2", "", "HCM", "South", "700000"),
                new Address("222 DUW ltd p2", "", "HCM", "South", "700000"));
        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);


        User user3 = new User();
        user3.setUsername("dungct");
        user3.setPassword("ttg");
        Customer customer3 = new Customer(null, "Dung", "CT",
                "dungct@gmail.com", "012323774078",
                new Address("789 DHE p3", "", "Ha Noi", "North", "738382"),
                new Address("333 DUW ltd p3", "", "Ha Noi", "North", "738382"));
        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);


        User user4 = new User();
        user4.setUsername("trananhdung");
        user4.setPassword("123");
        Customer customer4 = new Customer(null, "Dung", "Tran",
                "trananhdung@gmail.com", "023423423134",
                new Address("133 VKL p4", "", "HCM", "South", "700000"),
                new Address("444 DDW ltd p4", "", "HCM", "South", "700000"));
        user4.setCustomer(customer4);
        userService.saveOrUpdate(user4);

        User user5 = new User();
        user5.setUsername("johndoe");
        user5.setPassword("aaa");
        Customer customer5 = new Customer(null, "John", "Doe",
                "johndoe123@gmail.com", "023423423134",
                new Address("221 KKA p4", "", "HCM", "South", "700000"),
                new Address("120 SDU ltd p4", "", "HCM", "South", "700000"));
        user5.setCustomer(customer5);
        userService.saveOrUpdate(user5);

        User user6 = new User();
        user6.setUsername("sa");
        user6.setPassword("123");
        Customer customer6 = new Customer(null, "Admin", "Tigi",
                "tigiadmin@gmail.com", "023423423134",
                new Address("221 KKA p4", "", "HCM", "South", "700000"),
                new Address("120 SDU ltd p4", "", "HCM", "South", "700000"));
        user6.setCustomer(customer6);
        userService.saveOrUpdate(user6);
    }

    private void loadCurrentCart(){
        List<User> users = (List<User>) userService.listAll();
        List<Course> courses = (List<Course>) courseService.listAll();

        users.get(0).setCart(new Cart());
        users.get(0).getCart()
                .addCartDetail(new CartDetails(1, users.get(0).getCart(), courses.get(10)));
        users.get(0).getCart()
                .addCartDetail(new CartDetails(2, users.get(0).getCart(), courses.get(9)));
        users.get(0).getCart()
                .addCartDetail(new CartDetails(2, users.get(0).getCart(), courses.get(7)));
        userService.saveOrUpdate(users.get(0));


        users.get(1).setCart(new Cart());
        users.get(1).getCart()
                .addCartDetail(new CartDetails(1, users.get(1).getCart(), courses.get(0)));
        users.get(1).getCart()
                .addCartDetail(new CartDetails(4, users.get(1).getCart(), courses.get(7)));
        users.get(1).getCart()
                .addCartDetail(new CartDetails(3, users.get(1).getCart(), courses.get(13)));
        userService.saveOrUpdate(users.get(1));


        users.get(2).setCart(new Cart());
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), courses.get(3)));
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), courses.get(5)));
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), courses.get(6)));
        userService.saveOrUpdate(users.get(2));


//        users.get(3).setCart(new Cart());
//        users.get(3).getCart()
//                .addCartDetail(new CartDetails(1, users.get(3).getCart(), courses.get(0)));
//        users.get(3).getCart()
//                .addCartDetail(new CartDetails(4, users.get(3).getCart(), courses.get(1)));
//        users.get(3).getCart()
//                .addCartDetail(new CartDetails(2, users.get(3).getCart(), courses.get(2)));
//        users.get(3).getCart()
//                .addCartDetail(new CartDetails(3, users.get(3).getCart(), courses.get(5)));
//        userService.saveOrUpdate(users.get(3));
    }

    private void loadOrderHistory(){
        List<User> users = (List<User>) userService.listAll();
        List<Course> courses = (List<Course>) courseService.listAll();

        users.get(0).addOrders(new Order(users.get(0),
                        users.get(0).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.SHIPPED));
        users.get(0).addOrders(new Order(users.get(0),
                        users.get(0).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.SHIPPED));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(0).getOrders().get(0), courses.get(2)));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(0).getOrders().get(0), courses.get(1)));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(4, users.get(0).getOrders().get(0), courses.get(3)));
        users.get(0).getOrders().get(1)
                .addOrderDetails(new OrderDetails(2, users.get(0).getOrders().get(1), courses.get(4)));
        users.get(0).getOrders().get(1)
                .addOrderDetails(new OrderDetails(1, users.get(0).getOrders().get(1), courses.get(0)));
        userService.saveOrUpdate(users.get(0));


        users.get(1).addOrders(new Order(users.get(1),
                        users.get(1).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.SHIPPED));
        users.get(1).addOrders(new Order(users.get(1),
                        users.get(1).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.SHIPPED));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(1).getOrders().get(0), courses.get(5)));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(4, users.get(1).getOrders().get(0), courses.get(2)));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(1).getOrders().get(0), courses.get(1)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(6, users.get(1).getOrders().get(1), courses.get(4)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(4, users.get(1).getOrders().get(1), courses.get(8)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(1, users.get(1).getOrders().get(1), courses.get(3)));
        userService.saveOrUpdate(users.get(1));


        users.get(2).addOrders(new Order(users.get(2),
                        users.get(2).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.SHIPPED));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(2).getOrders().get(0), courses.get(1)));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(2).getOrders().get(0), courses.get(4)));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(2).getOrders().get(0), courses.get(2)));
        userService.saveOrUpdate(users.get(2));
    }

    private void loadOwnedCourse(){
        List<User> users = (List<User>) userService.listAll();
        List<Course> courses  = (List<Course>) courseService.listAll();

        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2), 4f, "Nice course!", ""));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1), 4f, "Very comprehensive", ""));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(3), 5f, "Thank you for create this course", ""));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4), 2f, "This course just an introduction course", ""));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(0), 3f, "I expect more real world example", "Thank you for you feed back, we will improve soon"));
        for (CourseOwner courseOwner :
            users.get(0).getCourseOwners()){

            for (CourseDetails courseDetails:
                 courseOwner.getCourse().getCourseDetails()) {

                for (CourseResource courseResource:
                        courseDetails.getCourseResources()) {
                    //users.get(0).addLearnTracking(new LearnTracking(false, 0l, courseResource));
                    LearnTracking learnTracking = new LearnTracking(false, 0l, courseResource);
                    learnTracking.setUser(users.get(0));
                    learnTrackingService.saveOrUpdate(learnTracking);
                }
            }
        }
        userService.saveOrUpdate(users.get(0));

        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(5), 0f, "", ""));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2), 4f, "I will recommend this course for my friend", ""));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1), 4.5f, "Good explanation", ""));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4), 3f, "This course is good but you accent is too hard to understand", ""));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(8), 0f, "", ""));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(3), 0f, "", ""));
        for (CourseOwner courseOwner :
                users.get(1).getCourseOwners()){

            for (CourseDetails courseDetails:
                    courseOwner.getCourse().getCourseDetails()) {

                for (CourseResource courseResource:
                        courseDetails.getCourseResources()) {
                    //users.get(1).addLearnTracking(new LearnTracking(false, 0l, courseResource));
                    LearnTracking learnTracking = new LearnTracking(false, 0l, courseResource);
                    learnTracking.setUser(users.get(1));
                    learnTrackingService.saveOrUpdate(learnTracking);
                }
            }
        }
        userService.saveOrUpdate(users.get(1));

        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1), 3f, "This course not complete at all", ""));
        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4), 4.5f, "Well explanation", ""));
        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2), 4f, "Very engagement", ""));
        for (CourseOwner courseOwner :
                users.get(2).getCourseOwners()){

            for (CourseDetails courseDetails:
                    courseOwner.getCourse().getCourseDetails()) {

                for (CourseResource courseResource:
                        courseDetails.getCourseResources()) {
                    //users.get(2).addLearnTracking(new LearnTracking(false, 0l, courseResource));
                    LearnTracking learnTracking = new LearnTracking(false, 0l, courseResource);
                    learnTracking.setUser(users.get(2));
                    learnTrackingService.saveOrUpdate(learnTracking);
                }
            }
        }
        userService.saveOrUpdate(users.get(2));

        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(0), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(1), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(2), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(3), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(4), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(5), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(6), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(7), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(8), 0f, "", ""));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(9), 0f, "", ""));
        userService.saveOrUpdate(users.get(3));

        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(10), 0f, "", ""));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(11), 0f, "", ""));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(12), 0f, "", ""));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(13), 0f, "", ""));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(14), 0f, "", ""));
        userService.saveOrUpdate(users.get(4));
    }

    private void loadRoles(){
        Role role1 = new Role(RoleType.STUDENT);
        roleService.saveOrUpdate(role1);

        Role role2 = new Role(RoleType.ADMIN);
        roleService.saveOrUpdate(role2);

        Role role3 = new Role(RoleType.TEACHER);
        roleService.saveOrUpdate(role3);
    }

    private void assignUsersToDefaultRole(){
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();


        users.get(0).addRole(roles.get(0));
        userService.saveOrUpdate(users.get(0));

        users.get(1).addRole(roles.get(0));
        userService.saveOrUpdate(users.get(1));

        users.get(2).addRole(roles.get(0));
        userService.saveOrUpdate(users.get(2));

        users.get(3).addRole(roles.get(2));
        userService.saveOrUpdate(users.get(3));

        users.get(4).addRole(roles.get(2));
        userService.saveOrUpdate(users.get(4));

        users.get(5).addRole(roles.get(1));
        userService.saveOrUpdate(users.get(5));
    }
}
