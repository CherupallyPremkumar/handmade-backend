package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProfileDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String avatarUrl;
    private String createdAt;
    private String updatedAt;
}
