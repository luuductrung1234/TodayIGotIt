package ldt.springframework.springmvc.services;

import ldt.springframework.springmvc.domain.Address;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.domain.User;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/17/18
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDAOImplTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListCustomers() throws Exception{
        List<Customer> customers = (List<Customer>) customerService.listAll();

        assert customers.size() == 4;
    }

    @Test
    public void testGetCustomerById() throws Exception{
        Customer customer = customerService.getById(2);

        assert customer.getId() == 2;
        assert customer.getFirstName().equals("Tuan");
        assert customer.getLastName().equals("Tran");
        assert customer.getEmail().equals("trananhtuan@gmail.com");
        assert customer.getPhoneNumber().equals("013242353248");
    }

    @Test
    public void testCreateNewCustomer() throws Exception{
        User newUser = new User();
        Customer newCustomer = new Customer(null, "Sang", "Duong", "sangthanhduong02@gmail.com",
                "03939343434",
                new Address("44/102 TP p4" , "", "Ben Tre", "South" , "493934"),
                new Address("44/102 TP p4" , "", "Ben Tre", "South" , "493934"));

        newUser.setCustomer(newCustomer);
        User savedUser = userService.saveOrUpdate(newUser);
        Customer savedCustomer = savedUser.getCustomer();

        // check generated ID
        assert savedCustomer.getId() == 5;

        // check amount of customers
        List<Customer> customers = (List<Customer>) customerService.listAll();
        assert customers.size() == 5;
    }

    @Test
    public void testEditCustomer() throws Exception{
        Customer utpCustomer = new Customer(1, "Tuyen", "Luu",
                "luukimtuyen@gmail.com", "01223774078",
                new Address("88/69 NVQ p1" , "", "HCM",
                "South" , "700000"),
                new Address("653 LVL NVQ p1" , "", "HCM",
                "South" , "700000"));
        utpCustomer.setVersion(0);
        Customer savedCustomer = customerService.saveOrUpdate(utpCustomer);

        // check returned customer after save
        assert savedCustomer.getId() == 1;
        assert savedCustomer.getFirstName().equals("Tuyen");
        assert savedCustomer.getLastName().equals("Luu");
        assert savedCustomer.getEmail().equals("luukimtuyen@gmail.com");
        assert savedCustomer.getPhoneNumber().equals("01223774078");
        assert savedCustomer.getBillingAddress().getAddressLine1().equals("88/69 NVQ p1");
        assert savedCustomer.getBillingAddress().getAddressLine2().equals("");
        assert savedCustomer.getBillingAddress().getCity().equals("HCM");
        assert savedCustomer.getBillingAddress().getState().equals("South");
        assert savedCustomer.getBillingAddress().getZipCode().equals("700000");
        assert savedCustomer.getShippingAddress().getAddressLine1().equals("653 LVL NVQ p1");
        assert savedCustomer.getShippingAddress().getAddressLine2().equals("");
        assert savedCustomer.getShippingAddress().getCity().equals("HCM");
        assert savedCustomer.getShippingAddress().getState().equals("South");
        assert savedCustomer.getShippingAddress().getZipCode().equals("700000");


        // check again
        User user = userService.getById(1);
        Customer customer = user.getCustomer();
        assert customer.getId() == 1;
        assert customer.getFirstName().equals("Tuyen");
        assert customer.getLastName().equals("Luu");
        assert customer.getEmail().equals("luukimtuyen@gmail.com");
        assert customer.getPhoneNumber().equals("01223774078");
        assert savedCustomer.getBillingAddress().getAddressLine1().equals("88/69 NVQ p1");
        assert savedCustomer.getBillingAddress().getAddressLine2().equals("");
        assert savedCustomer.getBillingAddress().getCity().equals("HCM");
        assert savedCustomer.getBillingAddress().getState().equals("South");
        assert savedCustomer.getBillingAddress().getZipCode().equals("700000");
        assert savedCustomer.getShippingAddress().getAddressLine1().equals("653 LVL NVQ p1");
        assert savedCustomer.getShippingAddress().getAddressLine2().equals("");
        assert savedCustomer.getShippingAddress().getCity().equals("HCM");
        assert savedCustomer.getShippingAddress().getState().equals("South");
        assert savedCustomer.getShippingAddress().getZipCode().equals("700000");
    }

    @Test
    public void testDeleteCustomer(){
        userService.delete(3);

        // check amount of customers
        List<Customer> customers = (List<Customer>) customerService.listAll();
        assert customers.size() == 3;

        // throw exception
        Customer notExistCustomer = customerService.getById(3);
        assert notExistCustomer == null;
    }
}