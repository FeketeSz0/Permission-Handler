package io.feketesz.login.services;

import io.feketesz.login.model.myUserDetails;
import io.feketesz.login.model.user;
import io.feketesz.login.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class myUserDetailsService implements UserDetailsService {

    @Autowired
    userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<user> user = userRepo.findByusername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("username '" + username + "' not found"));
        return user.map(u->new myUserDetails(u)).get();

    }
}
