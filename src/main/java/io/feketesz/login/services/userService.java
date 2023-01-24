package io.feketesz.login.services;

import io.feketesz.login.model.forms.changePasswordForm;
import io.feketesz.login.model.forms.registrationForm;
import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        if(form.getPassword().length() < 8){
            return "the minimal length is 8";
        }
        if(!form.getPassword().matches(".*[0-9].*")){
            return "password must contain a number";
        }
        if (!form.getPassword().matches(".*[A-Z].*")) {
            return "password must contain a capital";
        }

        user newUser = new user(form.getUsername(), new BCryptPasswordEncoder().encode(form.getPassword()), true, roleEnum.USER);
        userRepo.save(newUser);

        return "";
    }




    public String changePassword(changePasswordForm form, user user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(form.getOldPassword().equals("") || form.getNewPassword1().equals("")){
            return "field cannot be empty";
        }

        if (!form.getNewPassword1().equals(form.getNewPassword2())) {
          return "the passwords are not matching";
        }

        if(encoder.matches(user.getPassword(), form.getOldPassword())){

            return "your old password is incorrect";
        }
        if(form.getOldPassword().equals(form.getNewPassword1())){
            return "the new password cannot be the old one";
        }
        if(form.getNewPassword1().length() < 8){
            return "the minimal length is 8";
        }
        if(!form.getNewPassword1().matches(".*[0-9].*")){
            return "password must contain a number";
        }
        if (!form.getNewPassword1().matches(".*[A-Z].*")) {
            return "password must contain a capital";
        }



        user.setPassword(encoder.encode(form.getNewPassword1()));
        userRepo.save(user);
            return "";

    }

    public String passwordValidator(String password){



        return "";
    }

    public List<user> userList(){
       return userRepo.findAll();
    }

    public user finduser(String username){


        var user = userRepo.findByusername(username).orElseThrow(()->
                new NoSuchElementException("element not found"));



        return user;
    }

    public String deleteUserbyAdmin(user user, String confirm){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



       if(!encoder.matches(confirm,user.getPassword())){

           return "Password is incorrect";

       }
       if(confirm.isEmpty()){
           return "please enter your password";
       }


        userRepo.delete(user);
        return "";
    }


    public void editUser(user user) {
        var original = userRepo.findByusername(user.getUsername()).get();
        logger.info("Incoming user: \n " + user.getUsername() +"\n" + user.getIsActive());
        original.setIsActive(user.getIsActive());
        original.setRole(user.getRole());
        userRepo.save(original);


    }
    public String deleteUserbyAdmin(String deleteableUsername, String adminUsername){
        var deleteable = userRepo.findByusername(deleteableUsername).get();
        var admin = userRepo.findByusername(adminUsername).get();

        if(deleteable.getRole() == roleEnum.USER){
            userRepo.delete(deleteable);
            return "user deleted";
        } else if (deleteable.getRole() == roleEnum.ADMIN && admin.getRole() == roleEnum.MASTER) {
            userRepo.delete(deleteable);
            return "admin deleted";

        }else{
            return "no privilege to delete an admin. Please contact to a MASTER user";
        }
    }
}


