package io.feketesz.login;

import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunne(userRepo userRepo) {
        return args -> {
            userRepo.save(new user(1,"sample","sample",true, Arrays.asList(roleEnum.USER)));
        };
    }
}
