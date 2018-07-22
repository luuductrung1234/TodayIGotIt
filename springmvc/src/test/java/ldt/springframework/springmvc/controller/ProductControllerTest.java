package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Product;
import ldt.springframework.springmvc.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ProductControllerTest {

    // =======================================
    // =           Injection Point           =
    // =======================================

    // Mockito Mock object
    @Mock
    private ProductService productService;

    // setups up controller, and injects mock objects into it
    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        // initializes controller and mock object
        // the mock object will be automatically inject into the controller
        MockitoAnnotations.initMocks(this);

        // create a standalone MVC Context to mocking
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    // =======================================
    // =            Testing Unit             =
    // =======================================

    @Test
    public void testListProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        // specific Mockito interaction,
        // tell stub to return list of products
        when(productService.listAll()).thenReturn((List) products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/products"))
                .andExpect(model().attribute("products", hasSize(2)));
    }

    @Test
    public void testGetProductById() throws Exception {
        Integer testId = 1;

        // Tell Mockito stub to return new product for ID 1
        when(productService.getById(testId)).thenReturn(new Product());

        mockMvc.perform(get("/product/" + testId))
                .andExpect(status().isOk())
                .andExpect(view().name("product/product"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEditProduct() throws Exception {
        Integer testId = 1;

        // Tell Mockito stub to return new product for ID 1
        when(productService.getById(testId)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testCreateNewProduct() throws Exception {
        Integer testId = 1;

        // should not call the mock service
        verifyNoMoreInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testSaveOrUpdateProduct() throws Exception {
        // these data below is what you want to see after the Save and Update perform
        Integer id = 1;
        String description = "Test Product";
        BigDecimal price = new BigDecimal("12.00");
        String imageUrl = "example.com";
        Product expectReturnProduct = new Product(id, description, price, imageUrl);


        when(productService.saveOrUpdate(ArgumentMatchers.any(Product.class))).thenReturn(expectReturnProduct);

        mockMvc.perform(post("/product")
                .param("id", id.toString())
                .param("description", description)
                .param("price", price.toString())
                .param("imageUrl", imageUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:product/1"))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("description", is(description))))
                .andExpect(model().attribute("product", hasProperty("price", is(price))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));


        // After the ProductService's saveOrUpdate() is called the Mockito framework return an expectReturnObject
        // At this point we use Mockito to capture the return object
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());

        // verify properties of bound object
        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }


    @Test
    public void testDelete()throws Exception{
        Integer id = 1;

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        verify(productService, times(1)).delete(id);
    }
}