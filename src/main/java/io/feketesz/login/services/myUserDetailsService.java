package io.feketesz.login.services;

import io.feketesz.login.configurations.securityConfig;
import io.feketesz.login.model.myUserDetails;
import io.feketesz.login.model.roleEnum;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class myUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(securityConfig.class);
    @Autowired
    userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<user> user = userRepo.findByusername(username);
        for (roleEnum role : user.get().getRoles()) {
            logger.info("loadingByUserName, these are the user roles :" + role);
        };
        user.orElseThrow(() -> new UsernameNotFoundException("username '" + username + "' not found"));
        return user.map(u -> new myUserDetails(u)).get();

    }
}
