package com.homebase.admin.dto.user;

import com.homebase.admin.dto.TenantConfigDTO;

public class UserLoginResponse {
    private AuthUserDto user;
    private String token;
    private TenantConfigDTO tenant;

    // Private constructor for builder
    private UserLoginResponse(Builder builder) {
        this.user = builder.user;
        this.token = builder.token;
        this.tenant = builder.tenant;
    }

    // Getters
    public AuthUserDto getUser() { return user; }
    public String getToken() { return token; }
    public TenantConfigDTO getTenant() { return tenant; }

    // Builder class
    public static class Builder {
        private AuthUserDto user;
        private String token;
        private TenantConfigDTO tenant;

        public Builder user(AuthUserDto user) { this.user = user; return this; }
        public Builder token(String token) { this.token = token; return this; }
        public Builder tenant(TenantConfigDTO tenant) { this.tenant = tenant; return this; }

        public UserLoginResponse build() {
            return new UserLoginResponse(this);
        }
    }
}