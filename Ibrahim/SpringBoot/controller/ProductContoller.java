package Ibrahim.SpringBoot.controller;

import Ibrahim.SpringBoot.model.Agent;
import Ibrahim.SpringBoot.model.Product;
import Ibrahim.SpringBoot.repository.AgentRepository;
import Ibrahim.SpringBoot.repository.ProductRepository;
import Ibrahim.SpringBoot.service.AgentServiceImp;
import Ibrahim.SpringBoot.service.ProductServiceImp;
import Ibrahim.SpringBoot.service.StoreServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ProductContoller {

    @Autowired
    private ProductServiceImp pServ;

    @Autowired
    private AgentRepository aRepo;
    @Autowired
    private ProductRepository pRepo;

    @GetMapping("/showProducts")
    public ModelAndView showProducts(Model model, Authentication authentication, HttpSession session,@RequestParam(value = "bill", required = false) String bill) {
        ModelAndView mav=new ModelAndView("list-products");
        Agent agent = aRepo.readByEmail(authentication.getName());
        session.setAttribute("LoggedInAgent", agent);
        mav.addObject("products", pRepo.getAllStoreProduct(agent.getStore().getId()));
        mav.addObject("username", agent.getName());
        mav.addObject("roles", authentication.getAuthorities().toString());
        mav.addObject("bill",bill);
        return mav;
    }

    @GetMapping("/addProductForm")
    public String addProductForm(Model model, Authentication authentication, HttpSession session) {
        model.addAttribute("product", new Product());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        Agent agent = (Agent) session.getAttribute("LoggedInAgent");

        return "add-product-form.html";
    }


    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute Product newP, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            log.error("form error :" + errors.toString());
            return "add-product-form.html";
        }
        Agent agent = (Agent) session.getAttribute("LoggedInAgent");
        newP.setStore(agent.getStore());
        pServ.saveProduct(newP);
        return "redirect:/showProducts";
    }

    @GetMapping("/updateForm")
    public ModelAndView updateForm(@RequestParam Integer productId) {
        ModelAndView mav = new ModelAndView("add-product-form");
        mav.addObject("product", pServ.getProductById(productId));
        return mav;
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer productId) {
        pServ.deleteProduct(productId);
        return "redirect:/showProducts";
    }
}
