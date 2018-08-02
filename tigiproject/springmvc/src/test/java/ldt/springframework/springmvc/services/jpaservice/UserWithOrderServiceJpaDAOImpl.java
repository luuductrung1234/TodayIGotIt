package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.springmvc.domain.Course;
import ldt.springframework.springmvc.domain.Order;
import ldt.springframework.springmvc.domain.OrderDetails;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.CourseService;
import ldt.springframework.springmvc.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/22/18
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class UserWithOrderServiceJpaDAOImpl {


    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testAddOrderToUser() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        user.addOrders(new Order());
        user.addOrders(new Order());

        User savedUser = userService.saveOrUpdate(user);


        // Let Test After Add
        User getUser = userService.getById(savedUser.getId());

        assert getUser.getId() != null;
        assert getUser.getVersion() != null;
        assert getUser.getOrders().get(0) != null;
        assert getUser.getOrders().get(0).getId() != null;
        assert getUser.getOrders().get(1) != null;
        assert getUser.getOrders().get(1).getId() != null;
    }

    @Test
    public void testAddOrderWithOrderDetails() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        List<Course> storeCourses = (List<Course>) courseService.listAll();

        user.addOrders(new Order());
        OrderDetails orderItemOne = new OrderDetails();
        orderItemOne.setCourse(storeCourses.get(0));
        user.getOrders().get(0).addOrderDetails(orderItemOne);
        OrderDetails orderItemTwo = new OrderDetails();
        orderItemTwo.setCourse(storeCourses.get(1));
        user.getOrders().get(0).addOrderDetails(orderItemTwo);

        user.addOrders(new Order());
        OrderDetails orderItemThree = new OrderDetails();
        orderItemThree.setCourse(storeCourses.get(3));
        user.getOrders().get(1).addOrderDetails(orderItemThree);
        OrderDetails orderItemFour = new OrderDetails();
        orderItemFour.setCourse(storeCourses.get(4));
        user.getOrders().get(1).addOrderDetails(orderItemFour);
        OrderDetails orderItemFive = new OrderDetails();
        orderItemFive.setCourse(storeCourses.get(1));
        user.getOrders().get(1).addOrderDetails(orderItemFive);

        User savedUser = userService.saveOrUpdate(user);


        // Let Test After Add
        User getUser = userService.getById(savedUser.getId());

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getOrders() != null;
        assert savedUser.getOrders().size() == 2;

        assert savedUser.getOrders().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().size() == 2;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1).getId() != null;

        assert savedUser.getOrders().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().size() == 3;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2).getId() != null;
    }

    @Test
    public void testAddAndRemoveCartDetails() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        List<Course> storeCourses = (List<Course>) courseService.listAll();

        user.addOrders(new Order());
        OrderDetails orderItemOne = new OrderDetails();
        orderItemOne.setCourse(storeCourses.get(0));
        user.getOrders().get(0).addOrderDetails(orderItemOne);
        OrderDetails orderItemTwo = new OrderDetails();
        orderItemTwo.setCourse(storeCourses.get(1));
        user.getOrders().get(0).addOrderDetails(orderItemTwo);

        user.addOrders(new Order());
        OrderDetails orderItemThree = new OrderDetails();
        orderItemThree.setCourse(storeCourses.get(3));
        user.getOrders().get(1).addOrderDetails(orderItemThree);
        OrderDetails orderItemFour = new OrderDetails();
        orderItemFour.setCourse(storeCourses.get(4));
        user.getOrders().get(1).addOrderDetails(orderItemFour);
        OrderDetails orderItemFive = new OrderDetails();
        orderItemFive.setCourse(storeCourses.get(1));
        user.getOrders().get(1).addOrderDetails(orderItemFive);

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getOrders() != null;
        assert savedUser.getOrders().size() == 2;

        assert savedUser.getOrders().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().size() == 2;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1).getId() != null;

        assert savedUser.getOrders().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().size() == 3;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2).getId() != null;

        savedUser.getOrders().get(0).removeOrderDetail(savedUser.getOrders().get(0).getOrderDetails().get(0));

        userService.saveOrUpdate(savedUser);

        assert savedUser.getOrders().get(0).getOrderDetails().size() == 1;


        // Test After Remove And Save
        User getUser = userService.getById(savedUser.getId());

        assert getUser.getOrders().get(0).getOrderDetails().size() == 1;
    }

    @Test
    public void testAddAndRemoveEntireOrder(){
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        List<Course> storeCourses = (List<Course>) courseService.listAll();

        user.addOrders(new Order());
        OrderDetails orderItemOne = new OrderDetails();
        orderItemOne.setCourse(storeCourses.get(0));
        user.getOrders().get(0).addOrderDetails(orderItemOne);
        OrderDetails orderItemTwo = new OrderDetails();
        orderItemTwo.setCourse(storeCourses.get(1));
        user.getOrders().get(0).addOrderDetails(orderItemTwo);

        user.addOrders(new Order());
        OrderDetails orderItemThree = new OrderDetails();
        orderItemThree.setCourse(storeCourses.get(3));
        user.getOrders().get(1).addOrderDetails(orderItemThree);
        OrderDetails orderItemFour = new OrderDetails();
        orderItemFour.setCourse(storeCourses.get(4));
        user.getOrders().get(1).addOrderDetails(orderItemFour);
        OrderDetails orderItemFive = new OrderDetails();
        orderItemFive.setCourse(storeCourses.get(1));
        user.getOrders().get(1).addOrderDetails(orderItemFive);

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getOrders() != null;
        assert savedUser.getOrders().size() == 2;

        assert savedUser.getOrders().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().size() == 2;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(0).getOrderDetails().get(1).getId() != null;

        assert savedUser.getOrders().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().size() == 3;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(0).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(1).getId() != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2) != null;
        assert savedUser.getOrders().get(1).getOrderDetails().get(2).getId() != null;

        savedUser.removeOrders(savedUser.getOrders().get(0));

        userService.saveOrUpdate(savedUser);

        assert savedUser.getOrders().size() == 1;


        // Let Test After Remove And Save
        User getUser = userService.getById(savedUser.getId());

        assert getUser.getOrders().size() == 1;
    }

    @Test
    public void testGetUserCartWithCartDetails(){
        User user1 = userService.getById(1);
        List<User> users = (List<User>) userService.listAll();

        assert user1 != null;
        assert user1.getOrders() != null;
        assert user1.getOrders().size() == 2;
        assert user1.getOrders().get(0).getId() != null;
        assert user1.getOrders().get(0).getOrderDetails() != null;
        assert user1.getOrders().get(0).getOrderDetails().size() == 3;
        assert user1.getOrders().get(1).getId() != null;
        assert user1.getOrders().get(1).getOrderDetails() != null;
        assert user1.getOrders().get(1).getOrderDetails().size() == 2;


        User user3 = userService.getById(3);

        assert user3 != null;
        assert user3.getOrders() != null;
        assert user3.getOrders().size() == 1;
        assert user3.getOrders().get(0).getId() != null;
        assert user3.getOrders().get(0).getOrderDetails() != null;
        assert user3.getOrders().get(0).getOrderDetails().size() == 3;
    }
}
