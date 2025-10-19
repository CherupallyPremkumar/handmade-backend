package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Tenant;
import com.homebase.ecom.entity.TenantEntity;
import com.homebase.ecom.repository.TenantRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class TenantEntityStore implements EntityStore<Tenant> {
    

    private final TenantRepository tenantRepository;
    public TenantEntityStore(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }
    
    @Override
    public void store(Tenant domainTenant) {
        TenantEntity jpaTenantEntity = convertToJpaEntity(domainTenant);
        tenantRepository.save(jpaTenantEntity);
        
        if (domainTenant.getId() == null) {
            domainTenant.setId(jpaTenantEntity.getId());
        }
    }

    @Override
    public Tenant retrieve(String id) {
        return tenantRepository.findById(id)
                .map(this::convertToDomainModel)
                .orElse(null);
    }
    
    private TenantEntity convertToJpaEntity(Tenant domain) {
        TenantEntity jpa = new TenantEntity();
        
        if (domain.getId() != null) {
            jpa.setId(domain.getId());
        }
        
        jpa.setTenantCode(domain.getTenantCode());
        jpa.setTenantName(domain.getTenantName());
        jpa.setDisplayName(domain.getDisplayName());
        jpa.setUrlPath(domain.getUrlPath());
        jpa.setLogoUrl(domain.getLogoUrl());
        jpa.setCurrency(domain.getCurrency());
        jpa.setLocale(domain.getLocale());
        jpa.setTimezone(domain.getTimezone());
        jpa.setSupportEmail(domain.getSupportEmail());
        jpa.setSupportPhone(domain.getSupportPhone());
        
        return jpa;
    }
    
    private Tenant convertToDomainModel(TenantEntity jpa) {
        Tenant domain = new Tenant();
        
        domain.setId(jpa.getId());
        domain.setTenantCode(jpa.getTenantCode());
        domain.setTenantName(jpa.getTenantName());
        domain.setDisplayName(jpa.getDisplayName());
        domain.setUrlPath(jpa.getUrlPath());
        domain.setLogoUrl(jpa.getLogoUrl());
        domain.setCurrency(jpa.getCurrency());
        domain.setLocale(jpa.getLocale());
        domain.setTimezone(jpa.getTimezone());
        domain.setSupportEmail(jpa.getSupportEmail());
        domain.setSupportPhone(jpa.getSupportPhone());
        
        return domain;
    }
}
