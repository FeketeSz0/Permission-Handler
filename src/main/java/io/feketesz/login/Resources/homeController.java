package io.feketesz.login.Resources;

import io.feketesz.login.model.registrationForm;
import io.feketesz.login.services.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public class homeController {

    Logger logger = LoggerFactory.getLogger(homeController.class);
    @Autowired
    userService userService;


    @GetMapping("/login")
    public String loginPage(Principal principal, @RequestParam(value = "logout", required = false) boolean logout,
                            Model model,
                            @RequestParam(value = "registered", required = false) boolean registered) {

        if (logout) {
            model.addAttribute("logout", logout);
        }
        if(registered){
            model.addAttribute("registered",registered);
        }
        if (principal == null) {
            return "login";
        }
        return "redirect:logout";

    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage";
    }

    @GetMapping()
    public String index(Principal principal, Model model, Boolean isLoggedIn) {

        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }


    @GetMapping("/register")
    public String register(Principal principal, Model model) {
        registrationForm form = new registrationForm();
        model.addAttribute("form", form);
        if (principal == null) {
            return "register";
        }
        return "redirect:logout";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute registrationForm form) {

        model.addAttribute("form", form);
        logger.info("the put infos are " + form.getUsername() + " " + form.getPassword() + " " + form.getPassword2());

        String responseMsg = userService.register(form);
        if (!responseMsg.equals("")) {
            model.addAttribute("errorMsg", responseMsg);
            return "register";
        }


        return "redirect:login?registered=true";

    }

}
