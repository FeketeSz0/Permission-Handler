package io.feketesz.login.Resources;

import io.feketesz.login.model.user;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class homeController {

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

}
