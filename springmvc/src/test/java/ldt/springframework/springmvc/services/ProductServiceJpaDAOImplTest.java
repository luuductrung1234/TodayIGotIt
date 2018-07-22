package ldt.springframework.springmvc.services;

import com.sun.xml.internal.bind.v2.TODO;
import ldt.springframework.springmvc.config.JpaIntegrationConfig;
import ldt.springframework.springmvc.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

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
public class ProductServiceJpaDAOImplTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private ProductService productService;


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListProducts() throws Exception{
        List<Product> products = (List<Product>) productService.listAll();

        assert products.size() == 4;
    }

    @Test
    public void testGetProductByid() throws Exception{
        Product product = productService.getById(1);

        assert product.getId() == 1;
        assert product.getDescription().equals("Product 1");
        assert product.getPrice().equals(new BigDecimal("12.99"));
    }

    @Test
    public void testCreateNewProduct() throws Exception{
        Product newProduct = new Product(null, "Product 5", new BigDecimal("7.89"), "example.com/product5");
        Product savedProduct = productService.saveOrUpdate(newProduct);

        // test generated id
        assert savedProduct.getId() == 5;

        // test total products after add
        List<Product> products = (List<Product>) productService.listAll();
        assert products.size() == 5;
    }

    @Test
    public void testEditProduct() throws Exception{
        Product uptProduct = new Product(2, "Product 1 (Updated)", new BigDecimal("1.11"), "example.com/product1");
        uptProduct.setVersion(0);
        Product savedProduct = productService.saveOrUpdate(uptProduct);

        assert savedProduct.getId() == 2;
        assert savedProduct.getDescription().equals("Product 1 (Updated)");
        assert savedProduct.getPrice().equals(new BigDecimal("1.11"));
        assert savedProduct.getImageUrl().equals("example.com/product1");

        // test again
        Product product = productService.getById(2);
        assert product.getId() == 2;
        assert product.getDescription().equals("Product 1 (Updated)");
        assert product.getPrice().equals(new BigDecimal("1.11"));
        assert product.getImageUrl().equals("example.com/product1");
    }

    @Test
    public void testDeleteProduct() throws Exception{
        productService.delete(3);

        List<Product> products = (List<Product>) productService.listAll();

        assert products.size() == 3;
    }
}