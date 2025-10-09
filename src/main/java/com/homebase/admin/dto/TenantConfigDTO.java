package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantConfigDTO {
    private String id;
    private String name;
    private String subdomain;
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
}
