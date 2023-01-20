package io.feketesz.login.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String password;
    private boolean isActive;


    private List<roleEnum> Roles;

    public user() {
    }

    public user(String username, String password, boolean isActive, List<roleEnum> roles) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        Roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<roleEnum> getRoles() {
        return Roles;
    }

    public void setRoles(List<roleEnum> roles) {
        Roles = roles;
    }
}
