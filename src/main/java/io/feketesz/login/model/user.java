package io.feketesz.login.model;

import java.util.List;

public class user {
    private int id;
    private String username;
    private String password;
    private boolean isActive;
    private List<roleEnum> Roles;

    public user() {
    }

    public user(int id, String username, String password, boolean isActive, List<roleEnum> roles) {
        this.id = id;
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
