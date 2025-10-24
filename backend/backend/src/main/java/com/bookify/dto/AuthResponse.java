package com.bookify.dto;

import com.bookify.entity.AppRole;

import java.util.List;
import java.util.UUID;

public class AuthResponse {
    private String token;
    private UUID userId;
    private String email;
    private String fullName;
    private List<AppRole> roles;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, UUID userId, String email, String fullName, List<AppRole> roles) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public List<AppRole> getRoles() { return roles; }
    public void setRoles(List<AppRole> roles) { this.roles = roles; }
}
