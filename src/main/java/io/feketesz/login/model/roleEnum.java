package io.feketesz.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

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
