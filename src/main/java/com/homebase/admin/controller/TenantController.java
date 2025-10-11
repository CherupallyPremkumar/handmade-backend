package com.homebase.admin.controller;

import com.homebase.admin.dto.TenantConfigurationDTO;
import com.homebase.admin.dto.TenantConfigDTO;
import com.homebase.admin.entity.Tenant;
import com.homebase.admin.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    /**
     * GET /api/tenants/{tenantPath}
     * Returns tenant configuration by tenantPath (used in frontend TenantContext)
     */
    @GetMapping("/tenants/{tenantPath}")
    public ResponseEntity<TenantConfigDTO> getTenantByPath(@PathVariable String tenantPath) {
        Tenant tenant = tenantService.findByUrlPath(tenantPath);
        if (tenant == null) {
            return ResponseEntity.notFound().build();
        }

        // Convert Tenant entity to DTO for frontend
        TenantConfigDTO dto = new TenantConfigDTO.Builder()
                .id(tenant.getTenantCode()) // Use tenantCode for API
                .name(tenant.getTenantName())
                .displayName(tenant.getDisplayName())
                .urlPath(tenant.getUrlPath())
                .logoUrl(tenant.getLogoUrl())
                .currency(tenant.getCurrency())
                .theme(tenant.getTheme())
                .configuration(new TenantConfigurationDTO.Builder()
                        .primaryColor(tenant.getConfiguration().getPrimaryColor())
                        .enablePromotions(tenant.getConfiguration().getEnablePromotions())
                        .secondaryColor(tenant.getConfiguration().getSecondaryColor())
                        .enableReviews(tenant.getConfiguration().getEnableReviews())
                        .build())
                .build();

        return ResponseEntity.ok(dto);
    }
}
