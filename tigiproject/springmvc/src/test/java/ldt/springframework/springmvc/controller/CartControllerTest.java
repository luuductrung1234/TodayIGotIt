package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Order;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.enums.OrderStatus;
import ldt.springframework.springmvc.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/24/18
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("jpadao")
public class CartControllerTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private WebApplicationContext wac;

    @Autowired UserService userService;


    private MockMvc mockMvc;


    @Before
    public void setup() {
        // create a standalone MVC Context to mocking
        mockMvc = MockMvcBuilders.standaloneSetup(this.wac).build();
    }


    // =======================================
    // =            Testing Unit             =
    // =======================================
    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("cartController"));
    }

    @Test
    public void testCheckOut() throws Exception{
        User currentUser = userService.getById(1);

        HashMap<String, Object> sessionAttr = new HashMap<>();
        sessionAttr.put("curUser", currentUser);


        mockMvc.perform(get("/user/checkout").sessionAttrs(sessionAttr))
                .andExpect(status().isOk())
                .andExpect(view().name("view/order/preparePayment"))
                .andExpect(model().attribute("order", hasProperty("shipToAddress", is(currentUser.getCustomer().getShippingAddress()))))
                .andExpect(model().attribute("order", hasProperty("orderStatus", is(OrderStatus.NEW))));

        // After the CustomerService's saveOrUpdate() is called the Mockito framework return an expectReturnObject
        // At this point we use Mockito to capture the return object
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        Order boundOrder = orderCaptor.getValue();
        // verify properties of bound object
        assertEquals(currentUser.getCustomer().getShippingAddress(), boundOrder.getShipToAddress());
        assertNull(boundOrder.getDateShipped());
        assertEquals(OrderStatus.NEW, boundOrder.getOrderStatus());

        assert boundOrder.getOrderDetails() != null;
        assert boundOrder.getOrderDetails().size() == 3;
    }

    @Test
    public void testBuyNow() throws Exception{

    }
}