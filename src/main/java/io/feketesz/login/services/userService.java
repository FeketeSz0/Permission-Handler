package io.feketesz.login.services;

import io.feketesz.login.Resources.homeController;
import io.feketesz.login.model.registrationForm;
import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class userService {
    @Autowired
    userRepo userRepo;


    Logger logger = LoggerFactory.getLogger(userService.class);

    public String register(registrationForm form) {
        //password validate
        if (!form.getPassword().equals(form.getPassword2())) {
            return "the passwords are not matching";
        }
        boolean isUsernameTaken = userRepo.findByusername(form.getUsername()).isPresent();

        if (isUsernameTaken) {
            return "username is already taken";
        }

        if(form.getUsername().isEmpty() || form.getPassword().isEmpty()){
            return "the fields cannot be empty";
        }

        user newUser = new user(form.getUsername(), new BCryptPasswordEncoder().encode(form.getPassword()), true, List.of(roleEnum.USER));
        userRepo.save(newUser);

        return "";
    }




    public void changePassword(registrationForm form){
        if (!form.getPassword().equals(form.getPassword2())) {
            throw new RuntimeException("the passwords are not matching");
        }

           var user = userRepo.findByusername(form.getUsername());
            user.get().setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));

    }

    public List<user> userList(){
       return userRepo.findAll();
    }

    public user finduser(String username){


        var user = userRepo.findByusername(username).orElseThrow(()->
                new NoSuchElementException("element not found"));


        logger.info("This what userService/finduser found here: " + user + "whit this username " + username);
        return user;
    }



}


