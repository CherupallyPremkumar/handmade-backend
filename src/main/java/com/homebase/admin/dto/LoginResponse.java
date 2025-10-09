package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private AdminUserDTO user;
    private String token;
    private Boolean requiresTwoFactor;
    private String sessionId;
    private TenantConfigDTO tenantConfig;
}
