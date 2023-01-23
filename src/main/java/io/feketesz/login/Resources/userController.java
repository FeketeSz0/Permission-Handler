package io.feketesz.login.Resources;

import io.feketesz.login.model.forms.changePasswordForm;
import io.feketesz.login.model.roleEnum;
import io.feketesz.login.services.userService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/user")
public class userController {

    Logger logger = LoggerFactory.getLogger(homeController.class);
    @Autowired
    userService userService;





    @GetMapping()
    public String userPage(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            boolean isAdmin =  user.getRole() == roleEnum.ADMIN || user.getRole() == roleEnum.MASTER;
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", user);
        }
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage";
    }

    @GetMapping("/P")
    public String userEditPassword(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            boolean isAdmin =  user.getRole() == roleEnum.ADMIN ||user.getRole() == roleEnum.MASTER;
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", user);
        }
        changePasswordForm form = new changePasswordForm();
        model.addAttribute("form", form);

        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage2";
    }


    @PostMapping("/P")
    public String userEditPassword(Model model, @ModelAttribute changePasswordForm form, Principal principal, Boolean isLoggedIn) {

        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
        }

        model.addAttribute("isLoggedIn", isLoggedIn);


        var user = userService.finduser(principal.getName());


        model.addAttribute("form", form);
        String responseMsg = userService.changePassword(form, user);


        if (responseMsg.equals("")) {
            String successMsg = "password saved";
            model.addAttribute("successMsg", successMsg);

        } else {
            model.addAttribute("errorMsg", responseMsg);
        }

        return "userpage2";
    }


    @GetMapping("/D")
    public String userDeleteAccount(Principal principal, Model model, Boolean isLoggedIn) {
        if (principal == null) {
            isLoggedIn = false;
        } else {
            isLoggedIn = true;
            var user = userService.finduser(principal.getName());
            boolean isAdmin =  user.getRole() == roleEnum.ADMIN || user.getRole() == roleEnum.MASTER;
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", user);
        }

        String confirm = "";

        model.addAttribute("confirm", confirm);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "userpage3";
    }

    @PostMapping("/D")
    public String userDeleteAccount(Principal principal, Model model, HttpServletRequest request, HttpServletResponse response, Boolean isLoggedIn,
                                    String confirm) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        var user = userService.finduser(principal.getName());

        boolean isAdmin =  user.getRole() == roleEnum.ADMIN || user.getRole() == roleEnum.MASTER;
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("confirm", confirm);
        model.addAttribute("user", user);


        String responseMsg = userService.deleteUser(user, confirm);
        logger.info("response msg is " + responseMsg);

        if (!responseMsg.isEmpty()) {
            model.addAttribute("responseMsg", responseMsg);
            isLoggedIn = true;
            model.addAttribute("isLoggedIn", isLoggedIn);
            return "userpage3";
        }

        isLoggedIn = false;
        model.addAttribute("isLoggedIn", isLoggedIn);
        new SecurityContextLogoutHandler().logout(request, response, auth);

        return "index";
    }

}
