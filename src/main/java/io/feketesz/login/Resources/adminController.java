package io.feketesz.login.Resources;

import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.services.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/admin")
public class adminController {

    Logger logger = LoggerFactory.getLogger(adminController.class);
    @Autowired
    userService userService;



    @GetMapping()
    public String adminPage(Model model, Principal principal, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
            return "login";
        }
        isLoggedIn = true;
        var currentAdmin = userService.finduser(principal.getName());
        boolean isAdmin =  currentAdmin.getRole() == roleEnum.ADMIN || currentAdmin.getRole() == roleEnum.MASTER  ;
        model.addAttribute("isAdmin", isAdmin);


        model.addAttribute("isLoggedIn", isLoggedIn);
        var userlist = userService.userList();
        userlist.remove(currentAdmin);
        userlist.forEach(user -> logger.info("user is in the list: " + user.getUsername()));

        model.addAttribute("user", new user());
        model.addAttribute("userlist", userlist);
        return "admin";
    }

    @GetMapping("/edit/{username}")
    public String adminPageEdit(Model model, Principal principal, Boolean isLoggedIn, @PathVariable("username") String username) {
        if (principal == null) {
            isLoggedIn = false;
            return "login";
        }
        isLoggedIn = true;
        var currentAdmin = userService.finduser(principal.getName());
        boolean isAdmin =  currentAdmin.getRole() == roleEnum.ADMIN || currentAdmin.getRole() == roleEnum.MASTER;
        model.addAttribute("isAdmin", isAdmin);



        model.addAttribute("isLoggedIn", isLoggedIn);

        List<roleEnum> availableRoles = List.of(roleEnum.values());
        model.addAttribute("availableRoles", availableRoles);


        var user = userService.finduser(username);


        model.addAttribute("user", user);

        return "admin2";
    }

    @PostMapping("/edit/{username}")
    public String submitAdminPageEdit(Model model, Principal principal, Boolean isLoggedIn, @ModelAttribute user user) {
        if (principal == null) {
            isLoggedIn = false;
            return "login";
        }
        isLoggedIn = true;
        var currentAdmin = userService.finduser(principal.getName());
        boolean isAdmin =  currentAdmin.getRole() == roleEnum.ADMIN || currentAdmin.getRole() == roleEnum.MASTER;
        model.addAttribute("isAdmin", isAdmin);




        model.addAttribute("isLoggedIn", isLoggedIn);


        model.addAttribute("user",user);
        userService.editUser(user);

        return "redirect:/api/admin";


    }
    @PostMapping("/delete/{username}")
    public String deleteAccount(Model model, Principal principal, Boolean isLoggedIn, @PathVariable("username") String username){
        userService.deleteUser(username);
        if(principal == null){isLoggedIn = false; return "login";}
        isLoggedIn = true;
        model.addAttribute("isLoggedIn",isLoggedIn);
        return "redirect:/api/admin";
    }

}
