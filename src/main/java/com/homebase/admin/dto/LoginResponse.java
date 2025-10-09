package com.homebase.admin.dto;




public class LoginResponse {
    private AdminUserDTO user;
    private String token;
    private Boolean requiresTwoFactor;
    private String sessionId;
    private TenantConfigDTO tenantConfig;

    public LoginResponse(AdminUserDTO user, String token, Boolean requiresTwoFactor, String sessionId, TenantConfigDTO tenantConfig) {
        this.user = user;
        this.token = token;
        this.requiresTwoFactor = requiresTwoFactor;
        this.sessionId = sessionId;
        this.tenantConfig = tenantConfig;
    }

    public AdminUserDTO getUser() {
        return user;
    }

    public void setUser(AdminUserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getRequiresTwoFactor() {
        return requiresTwoFactor;
    }

    public void setRequiresTwoFactor(Boolean requiresTwoFactor) {
        this.requiresTwoFactor = requiresTwoFactor;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public TenantConfigDTO getTenantConfig() {
        return tenantConfig;
    }

    public void setTenantConfig(TenantConfigDTO tenantConfig) {
        this.tenantConfig = tenantConfig;
    }
}
