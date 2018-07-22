package ldt.springframework.springmvc.bootstrap;

import ldt.springframework.springmvc.domain.*;
import ldt.springframework.springmvc.enums.OrderStatus;
import ldt.springframework.springmvc.services.CustomerService;
import ldt.springframework.springmvc.services.ProductService;
import ldt.springframework.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
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
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadProduct();
        this.loadUser();
        this.loadCurrentCart();
        this.loadOrderHistory();
    }

    private void loadProduct() {
        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.saveOrUpdate(product1);


        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("9.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("10.09"));
        product3.setImageUrl("http://example.com/product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("34.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("14.08"));
        product5.setImageUrl("http://example.com/product5");
        productService.saveOrUpdate(product5);

        Product product6 = new Product();
        product6.setDescription("Product 6");
        product6.setPrice(new BigDecimal("20.05"));
        product6.setImageUrl("http://example.com/product6");
        productService.saveOrUpdate(product6);
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
    }

    private void loadCurrentCart(){
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.get(0).setCart(new Cart());
        users.get(0).getCart()
                .addCartDetail(new CartDetails(1, users.get(0).getCart(), products.get(0)));
        users.get(0).getCart()
                .addCartDetail(new CartDetails(2, users.get(0).getCart(), products.get(1)));
        users.get(0).getCart()
                .addCartDetail(new CartDetails(2, users.get(0).getCart(), products.get(2)));
        userService.saveOrUpdate(users.get(0));


        users.get(1).setCart(new Cart());
        users.get(1).getCart()
                .addCartDetail(new CartDetails(1, users.get(1).getCart(), products.get(1)));
        users.get(1).getCart()
                .addCartDetail(new CartDetails(4, users.get(1).getCart(), products.get(3)));
        users.get(1).getCart()
                .addCartDetail(new CartDetails(3, users.get(1).getCart(), products.get(2)));
        userService.saveOrUpdate(users.get(1));


        users.get(2).setCart(new Cart());
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), products.get(4)));
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), products.get(1)));
        users.get(2).getCart()
                .addCartDetail(new CartDetails(2, users.get(2).getCart(), products.get(2)));
        userService.saveOrUpdate(users.get(2));


        users.get(3).setCart(new Cart());
        users.get(3).getCart()
                .addCartDetail(new CartDetails(1, users.get(3).getCart(), products.get(0)));
        users.get(3).getCart()
                .addCartDetail(new CartDetails(4, users.get(3).getCart(), products.get(1)));
        users.get(3).getCart()
                .addCartDetail(new CartDetails(2, users.get(3).getCart(), products.get(2)));
        users.get(3).getCart()
                .addCartDetail(new CartDetails(3, users.get(3).getCart(), products.get(5)));
        userService.saveOrUpdate(users.get(3));
    }

    private void loadOrderHistory(){
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.get(0).addOrders(new Order(users.get(0),
                        users.get(0).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.NEW));
        users.get(0).addOrders(new Order(users.get(0),
                        users.get(0).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.NEW));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(0).getOrders().get(0), products.get(2)));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(0).getOrders().get(0), products.get(1)));
        users.get(0).getOrders().get(0)
                .addOrderDetails(new OrderDetails(4, users.get(0).getOrders().get(0), products.get(2)));
        users.get(0).getOrders().get(1)
                .addOrderDetails(new OrderDetails(2, users.get(0).getOrders().get(1), products.get(4)));
        users.get(0).getOrders().get(1)
                .addOrderDetails(new OrderDetails(1, users.get(0).getOrders().get(1), products.get(1)));
        userService.saveOrUpdate(users.get(0));


        users.get(1).addOrders(new Order(users.get(1),
                        users.get(1).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.NEW));
        users.get(1).addOrders(new Order(users.get(1),
                        users.get(1).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.NEW));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(1).getOrders().get(0), products.get(5)));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(4, users.get(1).getOrders().get(0), products.get(2)));
        users.get(1).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(1).getOrders().get(0), products.get(1)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(6, users.get(1).getOrders().get(1), products.get(4)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(4, users.get(1).getOrders().get(1), products.get(2)));
        users.get(1).getOrders().get(1)
                .addOrderDetails(new OrderDetails(1, users.get(1).getOrders().get(1), products.get(3)));
        userService.saveOrUpdate(users.get(1));


        users.get(2).addOrders(new Order(users.get(2),
                        users.get(2).getCustomer().getShippingAddress(),
                        new Date(),
                        OrderStatus.NEW));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(2, users.get(2).getOrders().get(0), products.get(1)));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(2).getOrders().get(0), products.get(4)));
        users.get(2).getOrders().get(0)
                .addOrderDetails(new OrderDetails(1, users.get(2).getOrders().get(0), products.get(2)));
        userService.saveOrUpdate(users.get(2));
    }
}
