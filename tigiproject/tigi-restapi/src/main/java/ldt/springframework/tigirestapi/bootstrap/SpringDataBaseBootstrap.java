package ldt.springframework.tigirestapi.bootstrap;

import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.OrderStatus;
import ldt.springframework.tigibusiness.enums.OwerType;
import ldt.springframework.tigibusiness.enums.RoleType;
import ldt.springframework.tigibusiness.services.CourseService;
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
        course1.setDescription("Course 1");
        course1.setPrice(new BigDecimal("12.99"));
        course1.setImageUrl("http://example.com/course1");
        course1.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course1);


        Course course2 = new Course();
        course2.setDescription("Course 2");
        course2.setPrice(new BigDecimal("9.99"));
        course2.setImageUrl("http://example.com/course2");
        course2.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course2);

        Course course3 = new Course();
        course3.setDescription("Course 3");
        course3.setPrice(new BigDecimal("10.09"));
        course3.setImageUrl("http://example.com/course3");
        course3.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course3);

        Course course4 = new Course();
        course4.setDescription("Course 4");
        course4.setPrice(new BigDecimal("34.99"));
        course4.setImageUrl("http://example.com/course4");
        course4.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course4);

        Course course5 = new Course();
        course5.setDescription("Course 5");
        course5.setPrice(new BigDecimal("14.08"));
        course5.setImageUrl("http://example.com/course5");
        course5.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course5);

        Course course6 = new Course();
        course6.setDescription("Course 6");
        course6.setPrice(new BigDecimal("20.05"));
        course6.setImageUrl("http://example.com/course6");
        course6.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course6);

        Course course7 = new Course();
        course7.setDescription("Course 7");
        course7.setPrice(new BigDecimal("19.99"));
        course7.setImageUrl("http://example.com/course7");
        course7.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course7);

        Course course8 = new Course();
        course8.setDescription("Course 8");
        course8.setPrice(new BigDecimal("19.99"));
        course8.setImageUrl("http://example.com/course8");
        course8.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course8);

        Course course9 = new Course();
        course9.setDescription("Course 9");
        course9.setPrice(new BigDecimal("20.05"));
        course9.setImageUrl("http://example.com/course9");
        course9.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course9);

        Course course10 = new Course();
        course10.setDescription("Course 10");
        course10.setPrice(new BigDecimal("10.50"));
        course10.setImageUrl("http://example.com/course10");
        course10.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course10);

        Course course11 = new Course();
        course11.setDescription("Course 11");
        course11.setPrice(new BigDecimal("2.05"));
        course11.setImageUrl("http://example.com/course11");
        course11.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course11);

        Course course12 = new Course();
        course12.setDescription("Course 12");
        course12.setPrice(new BigDecimal("10.99"));
        course12.setImageUrl("http://example.com/course12");
        course12.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course12);

        Course course13 = new Course();
        course13.setDescription("Course 13");
        course13.setPrice(new BigDecimal("90.50"));
        course13.setImageUrl("http://example.com/course13");
        course13.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course13);

        Course course14 = new Course();
        course14.setDescription("Course 14");
        course14.setPrice(new BigDecimal("199.99"));
        course14.setImageUrl("http://example.com/course14");
        course14.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
        courseService.saveOrUpdate(course14);

        Course course15 = new Course();
        course15.setDescription("Course 15");
        course15.setPrice(new BigDecimal("9.99"));
        course15.setImageUrl("http://example.com/course15");
        course15.setMediaPath("/Users/ldt/Downloads/Video/tut1.mp4");
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

        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2)));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1)));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(3)));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4)));
        users.get(0).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(0)));
        userService.saveOrUpdate(users.get(0));

        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(5)));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2)));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1)));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4)));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(8)));
        users.get(1).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(3)));
        userService.saveOrUpdate(users.get(1));

        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(1)));
        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(4)));
        users.get(2).addCourseOwer(new CourseOwner(OwerType.BUY, courses.get(2)));
        userService.saveOrUpdate(users.get(2));

        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(0)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(1)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(2)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(3)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(4)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(5)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(6)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(7)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(8)));
        users.get(3).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(9)));
        userService.saveOrUpdate(users.get(3));

        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(10)));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(11)));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(12)));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(13)));
        users.get(4).addCourseOwer(new CourseOwner(OwerType.CREATE, courses.get(14)));
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
