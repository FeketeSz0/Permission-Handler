package io.feketesz.login.Resources;

import io.feketesz.login.model.registrationForm;
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
        if (registered) {
            model.addAttribute("registered", registered);
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


    @GetMapping()
    public String index(Principal principal, Model model, Boolean isLoggedIn) {


        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var currentAdmin = userService.finduser(principal.getName());
            var isAdmin = currentAdmin.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);

        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal, Boolean isLoggedIn) {
        if(principal==null){
            isLoggedIn = false;
        }else{
            isLoggedIn= true;
            var currentAdmin = userService.finduser(principal.getName());
            var isAdmin = currentAdmin.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);

        }
        model.addAttribute("isLoggedIn",isLoggedIn);
        var userlist = userService.userList();
        userlist.forEach(user->logger.info("user is in the list: " + user.getUsername()));

        model.addAttribute("user", new user());
        model.addAttribute("userlist", userlist);
        return "admin";
    }

    @GetMapping("/admin/edit/{username}")
    public String adminPageEdit(Model model, Principal principal, Boolean isLoggedIn, @PathVariable("username") String username) {
        if(principal==null){
            isLoggedIn = false;
        }else{
            isLoggedIn= true;
            var currentAdmin = userService.finduser(principal.getName());
            var isAdmin = currentAdmin.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);

        }
        logger.info("the path variable is: " + username);
        model.addAttribute("isLoggedIn",isLoggedIn);

        List<roleEnum> availableRoles = List.of(roleEnum.values());
        model.addAttribute("availableRoles",availableRoles );


        var user = userService.finduser(username);
        logger.info("the found user is: " + user.getUsername());
        model.addAttribute("user", user);

        return "admin2";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            var isAdmin = user.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);
            model.addAttribute("user", user);
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage";
    }
    @GetMapping("/user/P")
    public String userEditPassword(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            var isAdmin = user.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);
            model.addAttribute("user", user);
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage2";
    }

    @GetMapping("/user/D")
    public String userDeleteAccount(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            var isAdmin = user.getRoles().stream().anyMatch(role -> role.getRole() == roleEnum.ADMIN.getRole());
            model.addAttribute("isAdmin",isAdmin);
            model.addAttribute("user", user);
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage3";
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

    @GetMapping("/403")
    public String forbidden(){
        return "403";
    }

}
