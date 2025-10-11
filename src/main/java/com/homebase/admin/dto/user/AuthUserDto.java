package com.homebase.admin.dto.user;

import java.util.List;

public class AuthUserDto {
    private String id;
    private String username;
    private String email;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    private List<String> roles;
    private String tenantId;

    // Private constructor for builder
    private AuthUserDto(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.roles = builder.roles;
        this.tenantId = builder.tenantId;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    // Builder class
    public static class Builder {
        private String id;
        private String username;
        private String email;
        private List<String> roles;
        private String tenantId;

        public Builder id(String id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder roles(List<String> role) { this.roles = roles; return this; }
        public Builder tenantId(String tenantId) { this.tenantId = tenantId; return this; }

        public AuthUserDto build() {
            return new AuthUserDto(this);
        }
    }
}