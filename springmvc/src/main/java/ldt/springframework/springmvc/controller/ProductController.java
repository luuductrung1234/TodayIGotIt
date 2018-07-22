package ldt.springframework.springmvc.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import ldt.springframework.springmvc.domain.Product;
import ldt.springframework.springmvc.domain.User;
import ldt.springframework.springmvc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */


@Controller
public class ProductController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    private ProductService productService;


    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String msg = "";
    private boolean failure;


    // =======================================
    // =          Business Methods           =
    // =======================================

    @RequestMapping("/products")
    public String listProducts(HttpServletRequest request, Model model){
        if(!failure){
            model.addAttribute("message", null);
        }else{
            model.addAttribute("message", msg);
        }
        model.addAttribute("products", productService.listAll());
        model.addAttribute("currentUser", request.getSession().getAttribute("curUser"));
        failure = false;

        return "view/product/products";
    }

    @RequestMapping("/product/{id}")
    public String getProductById(@PathVariable Integer id, HttpServletRequest request, Model model){
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/product/product";
    }

    @RequestMapping("/product/new")
    public String newProduct(HttpServletRequest request, Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/product/productform";
    }

    @RequestMapping("product/edit/{id}")
    public String editProduct(@PathVariable Integer id, HttpServletRequest request, Model model){
        model.addAttribute("product", productService.getById((id)));
        model.addAttribute("currentUser", (User) request.getSession().getAttribute("curUser"));

        return "view/product/productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Product product){
        try{
            Product savedProduct = productService.saveOrUpdate(product);
            failure = false;

            return "redirect:product/" + savedProduct.getId();
        }catch (Exception ex){
            msg = "Save fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/products";
    }


    @RequestMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model){
        try{
            productService.delete(id);
            failure = false;
        }catch (Exception ex){
            msg = "Delete fail! Something went wrong!";
            failure = true;
        }

        return "redirect:/products";
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @Autowired
    public void setProductService(ProductService productService){
        this.productService = productService;
    }
}
