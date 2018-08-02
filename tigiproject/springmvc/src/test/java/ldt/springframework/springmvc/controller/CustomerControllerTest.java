package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Address;
import ldt.springframework.springmvc.domain.Customer;
import ldt.springframework.springmvc.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    // Mockito Mock Object
    @Mock
    private CustomerService customerService;

    // setup controller, and injects mock object into it
    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;


    @Before
    public void setup(){
        // initialize controller and mock object
        // the mock object will be automatically inject into the controller
        MockitoAnnotations.initMocks(this);

        // create a standalone MVC Context to mocking
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListCustomers() throws Exception{
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception{
        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customer"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEditProduct() throws  Exception{
        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testCreateNewCustomer() throws Exception{
        verifyNoMoreInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdateCustomer() throws Exception{
        // these data below
        Integer id = 1;
        String firstName = "Trung";
        String lastName = "Luu";
        String email = "luuductrung1234@gmail.com";
        String phoneNumber = "01223453534";
        String billAddressLine1 = "43/33 HHT p2";
        String billAddressLine2 = "";
        String billCity = "HCM";
        String billState = "South";
        String billZipCode = "734334";
        String shipAddressLine1 = "43/33 HHT p2";
        String shipAddressLine2 = "";
        String shipCity = "HCM";
        String shipState = "South";
        String shipZipCode = "734334";
        Address billingAddress = new Address(billAddressLine1, billAddressLine2, billCity, billState, billZipCode);
        Address shippingAddress = new Address(shipAddressLine1, shipAddressLine2, shipCity, shipState, shipZipCode);
        Customer expectReturnCustomer = new Customer(id, firstName, lastName,
                email, phoneNumber,
                billingAddress, shippingAddress);


        when(customerService.saveOrUpdate(ArgumentMatchers.any(Customer.class))).thenReturn(expectReturnCustomer);

        mockMvc.perform(post("/customer")
                .param("id", id.toString())
                .param("firstName",firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("phoneNumber", phoneNumber)
                .param("billingAddress.addressLine1", billAddressLine1)
                .param("billingAddress.addressLine2", billAddressLine2)
                .param("billingAddress.city", billAddressLine2)
                .param("billingAddress.state", billAddressLine2)
                .param("billingAddress.zipCode", billAddressLine2)
                .param("shippingAddress.addressLine1", shipAddressLine1)
                .param("shippingAddress.addressLine2", shipAddressLine2)
                .param("shippingAddress.city", shipCity)
                .param("shippingAddress.state", shipState)
                .param("shippingAddress.zipCode", shipZipCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:customer/1"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
                .andExpect(model().attribute("customer", hasProperty("email", is(email))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("addressLine1", is(billAddressLine1)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("addressLine2", is(billAddressLine2)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("city", is(billCity)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("state", is(billState)))))
                .andExpect(model().attribute("customer", hasProperty("billingAddress", hasProperty("zipCode", is(billZipCode)))))
                .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("addressLine1", is(shipAddressLine1)))))
                .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("addressLine2", is(shipAddressLine2)))))
                .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("city", is(shipCity)))))
                .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("state", is(shipState)))))
                .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("zipCode", is(shipZipCode)))));


        // After the CustomerService's saveOrUpdate() is called the Mockito framework return an expectReturnObject
        // At this point we use Mockito to capture the return object
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(customerCaptor.capture());

        Customer boundCustomer = customerCaptor.getValue();
        // verify properties of bound object
        assertEquals(id, boundCustomer.getId());
        assertEquals(firstName, boundCustomer.getFirstName());
        assertEquals(lastName, boundCustomer.getLastName());
        assertEquals(email, boundCustomer.getEmail());
        assertEquals(phoneNumber, boundCustomer.getPhoneNumber());
        assertEquals(billAddressLine1, boundCustomer.getBillingAddress().getAddressLine1());
        assertEquals(billAddressLine2, boundCustomer.getBillingAddress().getAddressLine2());
        assertEquals(billCity, boundCustomer.getBillingAddress().getCity());
        assertEquals(billState, boundCustomer.getBillingAddress().getState());
        assertEquals(billZipCode, boundCustomer.getBillingAddress().getZipCode());
        assertEquals(shipAddressLine1, boundCustomer.getShippingAddress().getAddressLine1());
        assertEquals(shipAddressLine2, boundCustomer.getShippingAddress().getAddressLine2());
        assertEquals(shipCity, boundCustomer.getShippingAddress().getCity());
        assertEquals(shipState, boundCustomer.getShippingAddress().getState());
        assertEquals(shipZipCode, boundCustomer.getShippingAddress().getZipCode());
    }

    @Test
    public void testDelete()throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers"));

        verify(customerService, times(1)).delete(id);
    }

}