package com.homebase.admin.service;

import com.homebase.admin.entity.Tenant;
import com.homebase.admin.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    /**
     * Fetch tenant by URL path
     * @param urlPath tenant path from frontend
     * @return Tenant entity or null if not found
     */
    public Tenant findByUrlPath(String urlPath) {
        Optional<Tenant> tenantOpt = tenantRepository.findByUrlPath(urlPath);
        return tenantOpt.orElse(null);
    }
}