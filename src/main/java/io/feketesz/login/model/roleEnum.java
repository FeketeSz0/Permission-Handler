package io.feketesz.login.model;

public enum roleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;
    roleEnum(String user) {
        this.role = user;
    }

    public String getRole() {
        return role;
    }
}
