package com.homebase.admin.service;

import com.homebase.admin.entity.Tenant;
import com.homebase.admin.mapper.TenantMapper;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantMapper tenantMapper;

    public TenantService(TenantMapper tenantMapper) {
        this.tenantMapper = tenantMapper;
    }

    /**
     * Fetch tenant by URL path using MyBatis
     * @param urlPath tenant path from frontend
     * @return Tenant entity or null if not found
     */
    public Tenant findByUrlPath(String urlPath) {
        return tenantMapper.findByUrlPath(urlPath);
    }

    /**
     * Fetch tenant by ID
     */
    public Tenant findById(Long id) {
        return tenantMapper.findById(id);
    }

    /**
     * Fetch tenant by tenant code
     */
    public Tenant findByTenantCode(String tenantCode) {
        return tenantMapper.findByTenantCode(tenantCode);
    }
}