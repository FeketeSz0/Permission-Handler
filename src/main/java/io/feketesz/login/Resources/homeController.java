package io.feketesz.login.Resources;

import io.feketesz.login.model.registrationForm;
import io.feketesz.login.model.user;
import io.feketesz.login.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public class homeController {

    @Autowired
    userService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "logout";
    }

    @GetMapping("/user")
    public String userPage(){
        return "userpage";
    }

    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }


    @PostMapping("/register")
    public String register(@RequestParam registrationForm registrationForm){
        userService.register(registrationForm);
        return "registered";
    }

}
