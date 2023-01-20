package io.feketesz.login.services;

import io.feketesz.login.model.registrationForm;
import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    userRepo userRepo;

    public String register(registrationForm form) {
        //password validate
        if (!form.getPassword().equals(form.getPassword2())) {
            return "the passwords are not matching";
        }
        boolean isUsernameTaken = userRepo.findByusername(form.getUsername()).isPresent();

        if (isUsernameTaken) {
            return "username is already taken";
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

}
