package io.feketesz.login.Resources;

import io.feketesz.login.model.registrationForm;
import io.feketesz.login.model.user;
import io.feketesz.login.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class homeController {

    @Autowired
    userService userService;

    @GetMapping
    public String indexpage(){
        return "index";
    }


    @GetMapping("/user")
    public String userPage(){


        return "user";
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
