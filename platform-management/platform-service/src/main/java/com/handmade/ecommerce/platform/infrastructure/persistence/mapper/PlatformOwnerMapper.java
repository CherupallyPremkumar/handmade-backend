package com.handmade.ecommerce.platform.infrastructure.persistence.mapper;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.valueobject.*;
import com.handmade.ecommerce.platform.infrastructure.persistence.embeddable.*;
import com.handmade.ecommerce.platform.infrastructure.persistence.entity.PlatformOwnerJpaEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for PlatformOwner Domain ↔ JPA Entity conversion.
 * Infrastructure layer - NO business logic here.
 */
public class PlatformOwnerMapper {

    /**
     * Convert JPA Entity to Domain Model.
     */
    public static PlatformOwner toDomain(PlatformOwnerJpaEntity entity) {
        if (entity == null) return null;

        // Use reflection or constructor to create domain object
        // Since PlatformOwner has protected constructor, we'll use the bootstrap method for new instances
        // For existing instances loaded from DB, we need to reconstruct the state
        
        PlatformOwner domain = new PlatformOwner(); // Will need to make this accessible or use reflection
        
        // Set identity
        domain.setId(entity.getId());
        
        // Map value objects
        if (entity.getBrandIdentity() != null) {
            BrandIdentityEmbeddable brandEmb = entity.getBrandIdentity();
            BrandIdentity brandIdentity = new BrandIdentity(
                URI.create(brandEmb.getLogoUrl()),
                URI.create(brandEmb.getFaviconUrl()),
                brandEmb.getPrimaryColorHex(),
                brandEmb.getSupportEmail()
            );
            // domain.setBrandIdentity(brandIdentity); // Need setter or reflection
        }
        
        if (entity.getCorporateIdentity() != null) {
            CorporateIdentityEmbeddable corpEmb = entity.getCorporateIdentity();
            CorporateIdentity corporateIdentity = new CorporateIdentity(
                corpEmb.getLegalName(),
                corpEmb.getTaxId(),
                corpEmb.getRegistrationNumber(),
                corpEmb.getJurisdiction()
            );
            // domain.setCorporateIdentity(corporateIdentity);
        }
        
        // Map ComplianceMandate
        List<String> jurisdictions = entity.getComplianceAllowedJurisdictions() != null
            ? Arrays.asList(entity.getComplianceAllowedJurisdictions().split(","))
            : Collections.emptyList();
        ComplianceMandate complianceMandate = new ComplianceMandate(
            entity.getComplianceRequiresKyc() != null ? entity.getComplianceRequiresKyc() : false,
            entity.getComplianceRequiresTaxId() != null ? entity.getComplianceRequiresTaxId() : false,
            jurisdictions
        );
        
        // Map OperationalLimits
        OperationalLimits operationalLimits = new OperationalLimits();
        if (entity.getOperationalMaxTransactionAmount() != null) {
            operationalLimits.setMaxTransactionAmount(entity.getOperationalMaxTransactionAmount());
        }
        if (entity.getOperationalMaxSellersPerDay() != null) {
            operationalLimits.setMaxSellersPerDay(entity.getOperationalMaxSellersPerDay());
        }
        if (entity.getOperationalGlobalRateLimit() != null) {
            operationalLimits.setGlobalRateLimit(entity.getOperationalGlobalRateLimit());
        }
        
        // Map LocalizationPolicy
        LocalizationPolicy localizationPolicy = new LocalizationPolicy();
        localizationPolicy.setPrimaryCurrency(entity.getLocalizationPrimaryCurrency());
        localizationPolicy.setSupportedCurrencies(entity.getLocalizationSupportedCurrencies());
        localizationPolicy.setDefaultLanguage(entity.getLocalizationDefaultLanguage());
        localizationPolicy.setSupportedLanguages(entity.getLocalizationSupportedLanguages());
        localizationPolicy.setTimezone(entity.getLocalizationTimezone());
        
        // Map lifecycle state
        if (entity.getLifecycleState() != null) {
            // domain.setLifecycleState(PlatformLifecycle.valueOf(entity.getLifecycleState()));
        }
        
        // Map other fields
        domain.setCurrentVersion(entity.getCurrentVersion());
        domain.setCurrentStateId(entity.getCurrentStateId());
        domain.setFlowId(entity.getFlowId());
        
        return domain;
    }

    /**
     * Convert Domain Model to JPA Entity.
     */
    public static PlatformOwnerJpaEntity toEntity(PlatformOwner domain) {
        if (domain == null) return null;

        PlatformOwnerJpaEntity entity = new PlatformOwnerJpaEntity();
        
        // Set identity
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        
        // Map lifecycle state
        if (domain.getLifecycleState() != null) {
            entity.setLifecycleState(domain.getLifecycleState().name());
        }
        
        // Map BrandIdentity
        if (domain.getBrandIdentity() != null) {
            BrandIdentity brand = domain.getBrandIdentity();
            BrandIdentityEmbeddable brandEmb = new BrandIdentityEmbeddable(
                brand.logoUrl().toString(),
                brand.faviconUrl().toString(),
                brand.primaryColorHex(),
                brand.supportEmail()
            );
            entity.setBrandIdentity(brandEmb);
        }
        
        // Map CorporateIdentity
        if (domain.getCorporateIdentity() != null) {
            CorporateIdentity corp = domain.getCorporateIdentity();
            CorporateIdentityEmbeddable corpEmb = new CorporateIdentityEmbeddable(
                corp.legalName(),
                corp.taxId(),
                corp.registrationNumber(),
                corp.jurisdiction()
            );
            entity.setCorporateIdentity(corpEmb);
        }
        
        // Map ComplianceMandate
        if (domain.getComplianceMandate() != null) {
            ComplianceMandate compliance = domain.getComplianceMandate();
            entity.setComplianceRequiresKyc(compliance.isRequiresSellerKYC());
            entity.setComplianceRequiresTaxId(compliance.isRequiresTaxId());
            if (compliance.getAllowedJurisdictions() != null && !compliance.getAllowedJurisdictions().isEmpty()) {
                entity.setComplianceAllowedJurisdictions(
                    String.join(",", compliance.getAllowedJurisdictions())
                );
            }
        }
        
        // Map OperationalLimits
        if (domain.getOperationalLimits() != null) {
            OperationalLimits limits = domain.getOperationalLimits();
            entity.setOperationalMaxTransactionAmount(limits.getMaxTransactionAmount());
            entity.setOperationalMaxSellersPerDay(limits.getMaxSellersPerDay());
            entity.setOperationalGlobalRateLimit(limits.getGlobalRateLimit());
        }
        
        // Map LocalizationPolicy
        if (domain.getLocalizationPolicy() != null) {
            LocalizationPolicy localization = domain.getLocalizationPolicy();
            entity.setLocalizationPrimaryCurrency(localization.getPrimaryCurrency());
            entity.setLocalizationSupportedCurrencies(localization.getSupportedCurrencies());
            entity.setLocalizationDefaultLanguage(localization.getDefaultLanguage());
            entity.setLocalizationSupportedLanguages(localization.getSupportedLanguages());
            entity.setLocalizationTimezone(localization.getTimezone());
        }
        
        // Map other fields
        entity.setCurrentVersion(domain.getCurrentVersion());
        entity.setCurrentStateId(domain.getCurrentStateId());
        entity.setFlowId(domain.getFlowId());
        entity.setActiveCommissionPolicyId(domain.getActiveCommissionPolicyId());
        entity.setActiveFeatureConfigId(domain.getActiveFeatureConfigId());
        
        return entity;
    }
}
