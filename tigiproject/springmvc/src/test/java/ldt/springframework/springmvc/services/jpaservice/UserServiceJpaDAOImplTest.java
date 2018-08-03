package ldt.springframework.springmvc.services.jpaservice;

import ldt.springframework.tigibusiness.domain.*;
import ldt.springframework.tigibusiness.services.CourseService;
import ldt.springframework.tigibusiness.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class UserServiceJpaDAOImplTest {

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
    public void testSaveOfUser()
    throws Exception{
        User user = new User();

        user.setUsername("luuductrung1234");
        user.setPassword("Trung1997");

        User saveUser = userService.saveOrUpdate(user);

        assert saveUser.getId() != null;
        assert saveUser.getEncryptedPassowrd() != null;

        System.out.println("Encrypted Password");
        System.out.println(saveUser.getEncryptedPassowrd());
    }

    @Test
    public void testSaveOfUSerWithCustomer() throws Exception{
        User user = new User();
        user.setUsername("sadwindsang2002");
        user.setPassword("sang123");
        Customer customer = new Customer(null, "Sang", "Duong", "sangthanhduong02@gmail.com",
                "03939343434",
                new Address("44/102 TP p4" , "", "Ben Tre", "South" , "493934"),
                new Address("44/102 TP p4" , "", "Ben Tre", "South" , "493934"));
        user.setCustomer(customer);


        User savedUser = userService.saveOrUpdate(user);

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getCustomer() != null;
        assert savedUser.getCustomer().getId() != null;

        assert savedUser.getId() == 5;
        assert savedUser.getCustomer().getId() == 5;
    }


    @Test
    public void testGetFullUserDetails(){
        User user = userService.getById(1);

        assert user.getId() == 1;
        assert user.getCustomer() != null;
        assert user.getCustomer().getId() == 1;
    }
}