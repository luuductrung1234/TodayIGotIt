package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.domain.CartDetails;
import ldt.springframework.tigibusiness.domain.Course;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.UserService;
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
public class UserWithCartServiceJpaDAOImplTest {


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
    public void testAddCartToUser() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        user.setCart(new Cart());

        User savedUser = userService.saveOrUpdate(user);


        // Let Test After Add
        User getUser = userService.getById(savedUser.getId());
        assert getUser.getId() != null;
        assert getUser.getVersion() != null;
        assert getUser.getCart() != null;
        assert getUser.getCart().getId() != null;
    }

    @Test
    public void testAddCartWithCartDetails() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        user.setCart(new Cart());

        List<Course> storeCourses = (List<Course>) courseService.listAll();

        CartDetails cartItemOne = new CartDetails();
        cartItemOne.setCourse(storeCourses.get(0));
        user.getCart().addCartDetail(cartItemOne);

        CartDetails cartItemTwo = new CartDetails();
        cartItemTwo.setCourse(storeCourses.get(1));
        user.getCart().addCartDetail(cartItemTwo);

        User savedUser = userService.saveOrUpdate(user);


        // Let Test After Add
        User getUser = userService.getById(savedUser.getId());
        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getCart() != null;
        assert savedUser.getCart().getId() != null;
        assert savedUser.getCart().getCartDetails().size() == 2;
    }

    @Test
    public void testAddAndRemoveCartWithCartDetails() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");

        user.setCart(new Cart());

        List<Course> storeCourses = (List<Course>) courseService.listAll();

        CartDetails cartItemOne = new CartDetails();
        cartItemOne.setCourse(storeCourses.get(0));
        user.getCart().addCartDetail(cartItemOne);

        CartDetails cartItemTwo = new CartDetails();
        cartItemTwo.setCourse(storeCourses.get(1));
        user.getCart().addCartDetail(cartItemTwo);

        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getCart().getCartDetails().size() == 2;

        savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));

        userService.saveOrUpdate(savedUser);

        assert savedUser.getCart().getCartDetails().size() == 1;


        // Let Test After Remove And Save
        User getUser = userService.getById(savedUser.getId());

        assert savedUser.getCart().getCartDetails().size() == 1;
    }

    @Test
    public void testGetUserCartWithCartDetails(){
        User user1 = userService.getById(1);
        List<User> users = (List<User>) userService.listAll();

        assert user1 != null;
        assert user1.getCart() != null;
        assert user1.getCart().getId() != null;
        assert user1.getCart().getCartDetails() != null;
        assert user1.getCart().getCartDetails().size() == 3;

        User user4 = userService.getById(4);


        assert user4 != null;
        assert user4.getCart() != null;
        assert user4.getCart().getId() != null;
        assert user4.getCart().getCartDetails() != null;
        assert user4.getCart().getCartDetails().size() == 4;
    }
}
