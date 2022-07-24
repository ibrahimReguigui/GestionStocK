package Ibrahim.SpringBoot.controller;

import Ibrahim.SpringBoot.model.Product;
import Ibrahim.SpringBoot.service.ProductServiceImp;
import Ibrahim.SpringBoot.service.StoreServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
public class ProductContoller {

    @Autowired
    private ProductServiceImp pServ;
@Autowired
private StoreServiceImp sServ;

    @GetMapping("/showProducts")
    public String showProducts(Model model, Authentication authentication) {

        model.addAttribute("products", pServ.getAllProduct());
        model.addAttribute("username",authentication.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
        return "list-products.html";
    }

    @GetMapping("/addProductForm")
    public String addProductForm(Model model, Authentication authentication) {
        model.addAttribute("product", new Product(null,0,0,null,null,sServ.getStoreById(1)));
        model.addAttribute("username",authentication.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
        /*throw new RuntimeException("dddddddddddddd");*/
        return "add-product-form.html";
    }


    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute Product newP , Errors errors) {
        if (errors.hasErrors()){
            log.error("form error :"+errors.toString());
            return "add-product-form.html";
        }
        pServ.saveProduct(newP);
        return "redirect:/showProducts";
    }

    @GetMapping("/updateForm")
    public ModelAndView updateForm(@RequestParam Integer productId) {
        ModelAndView mav = new ModelAndView("add-product-form");
        mav.addObject("product",  pServ.getProductById(productId));
        return mav;
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer productId) {
        pServ.deleteProduct(productId);
        return "redirect:/showProducts";
    }
}