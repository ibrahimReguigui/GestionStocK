package Ibrahim.SpringBoot.controller;

import Ibrahim.SpringBoot.model.Agent;
import Ibrahim.SpringBoot.service.AgentServiceImp;
import Ibrahim.SpringBoot.service.RolesServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@Slf4j
@Controller
public class AgentContoller {
    @Autowired
    private AgentServiceImp aServ;

  /*  @Autowired
    private RolesServiceImp rServ;
*/
    /*@GetMapping("/addAgentForm")
    public ModelAndView addProductForm() {
        log.error("aaaaaaaaaaaaaaaaaaa"+rServ.getRoles().toString());
        ModelAndView mav = new ModelAndView("register-agent");
        mav.addObject("agent", new Agent());
        mav.addObject("roles",rServ.getRoles());
        return mav;
    }*/


    @PostMapping("/saveAgent")
    public String saveProduct(@Valid @ModelAttribute Agent newA , Errors errors) {

        if (errors.hasErrors()){
            log.error("form error :"+errors.toString());
            return "register-agent.html";
        }
        aServ.saveAgent(newA);
        return "redirect:/login?register=true";
    }
}
