package ldt.springframework.springmvc.controller;

import ldt.springframework.springmvc.domain.Product;
import ldt.springframework.springmvc.services.ProductService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */


@Controller
public class IndexController {

    // =======================================
    // =           Injection Point           =
    // =======================================

    @Autowired
    private ProductService productService;



    // =======================================
    // =           Request Mapping            =
    // =======================================

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model){
        List<Product> products = (List<Product>) productService.listAll();

        model.addAttribute("currentUser", request.getSession().getAttribute("curUser"));
        model.addAttribute("products", products);

        return "index";
    }

}
