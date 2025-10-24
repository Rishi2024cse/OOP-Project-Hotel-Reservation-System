package com.bookify.entity;

public enum AppRole {
    ADMIN("admin"),
    USER("user");

    private final String value;

    AppRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AppRole fromString(String value) {
        for (AppRole role : AppRole.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
