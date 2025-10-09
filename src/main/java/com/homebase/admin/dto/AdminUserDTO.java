package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
    private String id;
    private String email;
    private String name;
    private String role;
    private String avatarUrl;
    private String lastLogin;
    private String tenantId;
}
