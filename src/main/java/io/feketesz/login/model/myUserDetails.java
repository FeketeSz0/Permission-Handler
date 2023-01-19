package io.feketesz.login.model;

import io.feketesz.login.configurations.securityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class myUserDetails implements UserDetails {
    Logger logger = LoggerFactory.getLogger(myUserDetails.class);
    private String username;
    private String password;
    private Boolean isActive;
    private List<roleEnum> roles;


    public myUserDetails(user user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.roles = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(roleEnum role: roles){
            logger.info("role added to grantedAuthority: " + role.getRole());
            authorities.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
        }

        for(var a :authorities){
            logger.info("these are the content of auhtorities " + a);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
