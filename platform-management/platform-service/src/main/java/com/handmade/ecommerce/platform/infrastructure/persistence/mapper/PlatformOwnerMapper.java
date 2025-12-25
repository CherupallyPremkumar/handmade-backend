package com.handmade.ecommerce.platform.infrastructure.persistence.mapper;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.valueobject.*;
import com.handmade.ecommerce.platform.infrastructure.persistence.embeddable.BrandIdentityEmbeddable;
import com.handmade.ecommerce.platform.infrastructure.persistence.embeddable.CorporateIdentityEmbeddable;
import com.handmade.ecommerce.platform.infrastructure.persistence.entity.PlatformOwnerJpaEntity;

/**
 * Mapper between PlatformOwner (domain model) and PlatformOwnerJpaEntity (JPA entity)
 * Domain model is used by STM for business logic
 * JPA entity is used for database persistence
 */
public class PlatformOwnerMapper {

    /**
     * Convert domain model to JPA entity for persistence
     */
    public static PlatformOwnerJpaEntity toEntity(PlatformOwner domain) {
        if (domain == null) return null;

        PlatformOwnerJpaEntity entity = new PlatformOwnerJpaEntity();
        
        // Identity
        entity.setId(domain.id);
        entity.setName(domain.name);
        
        // State machine fields
        entity.setCurrentStateId(domain.getCurrentStateId());
        entity.setFlowId(domain.getFlowId());
        
        // Brand Identity
        if (domain.brandIdentity != null) {
            BrandIdentityEmbeddable brandEmbeddable = new BrandIdentityEmbeddable();
            brandEmbeddable.setBrandName(domain.brandIdentity.brandName);
            brandEmbeddable.setLogoUrl(domain.brandIdentity.logoUrl);
            brandEmbeddable.setTagline(domain.brandIdentity.tagline);
            brandEmbeddable.setPrimaryColor(domain.brandIdentity.primaryColor);
            brandEmbeddable.setSecondaryColor(domain.brandIdentity.secondaryColor);
            entity.setBrandIdentity(brandEmbeddable);
        }
        
        // Corporate Identity
        if (domain.corporateIdentity != null) {
            CorporateIdentityEmbeddable corpEmbeddable = new CorporateIdentityEmbeddable();
            corpEmbeddable.setLegalName(domain.corporateIdentity.legalName);
            corpEmbeddable.setRegistrationNumber(domain.corporateIdentity.registrationNumber);
            corpEmbeddable.setTaxId(domain.corporateIdentity.taxId);
            corpEmbeddable.setCountryOfIncorporation(domain.corporateIdentity.countryOfIncorporation);
            entity.setCorporateIdentity(corpEmbeddable);
        }
        
        // Policy References
        entity.setActiveCommissionPolicyId(domain.activeCommissionPolicyId);
        entity.setActiveFeatureConfigId(domain.activeFeatureConfigId);
        
        // Compliance Mandate (flattened)
        if (domain.complianceMandate != null) {
            entity.setComplianceRequiresKyc(domain.complianceMandate.requiresKyc);
            entity.setComplianceRequiresTaxId(domain.complianceMandate.requiresTaxId);
            // Convert list to CSV
            if (domain.complianceMandate.allowedJurisdictions != null) {
                entity.setComplianceAllowedJurisdictions(
                    String.join(",", domain.complianceMandate.allowedJurisdictions)
                );
            }
        }
        
        // Operational Limits (flattened)
        if (domain.operationalLimits != null) {
            entity.setOperationalMaxTransactionAmount(domain.operationalLimits.maxTransactionAmount);
            entity.setOperationalMaxSellersPerDay(domain.operationalLimits.maxSellersPerDay);
            entity.setOperationalGlobalRateLimit(domain.operationalLimits.globalRateLimit);
        }
        
        // Localization Policy (flattened)
        if (domain.localizationPolicy != null) {
            entity.setLocalizationPrimaryCurrency(domain.localizationPolicy.primaryCurrency);
            entity.setLocalizationDefaultLanguage(domain.localizationPolicy.defaultLanguage);
            entity.setLocalizationTimezone(domain.localizationPolicy.timezone);
            
            // Convert lists to CSV
            if (domain.localizationPolicy.supportedCurrencies != null) {
                entity.setLocalizationSupportedCurrencies(
                    String.join(",", domain.localizationPolicy.supportedCurrencies)
                );
            }
            if (domain.localizationPolicy.supportedLanguages != null) {
                entity.setLocalizationSupportedLanguages(
                    String.join(",", domain.localizationPolicy.supportedLanguages)
                );
            }
        }
        
        return entity;
    }

    /**
     * Convert JPA entity to domain model for business logic
     */
    public static PlatformOwner toDomain(PlatformOwnerJpaEntity entity) {
        if (entity == null) return null;

        PlatformOwner domain = new PlatformOwner();
        
        // Identity
        domain.id = entity.getId();
        domain.name = entity.getName();
        
        // State machine fields
        domain.setCurrentStateId(entity.getCurrentStateId());
        domain.setFlowId(entity.getFlowId());
        
        // Brand Identity
        if (entity.getBrandIdentity() != null) {
            BrandIdentity brandIdentity = new BrandIdentity();
            brandIdentity.brandName = entity.getBrandIdentity().getBrandName();
            brandIdentity.logoUrl = entity.getBrandIdentity().getLogoUrl();
            brandIdentity.tagline = entity.getBrandIdentity().getTagline();
            brandIdentity.primaryColor = entity.getBrandIdentity().getPrimaryColor();
            brandIdentity.secondaryColor = entity.getBrandIdentity().getSecondaryColor();
            domain.brandIdentity = brandIdentity;
        }
        
        // Corporate Identity
        if (entity.getCorporateIdentity() != null) {
            CorporateIdentity corpIdentity = new CorporateIdentity();
            corpIdentity.legalName = entity.getCorporateIdentity().getLegalName();
            corpIdentity.registrationNumber = entity.getCorporateIdentity().getRegistrationNumber();
            corpIdentity.taxId = entity.getCorporateIdentity().getTaxId();
            corpIdentity.countryOfIncorporation = entity.getCorporateIdentity().getCountryOfIncorporation();
            domain.corporateIdentity = corpIdentity;
        }
        
        // Policy References
        domain.activeCommissionPolicyId = entity.getActiveCommissionPolicyId();
        domain.activeFeatureConfigId = entity.getActiveFeatureConfigId();
        
        // Compliance Mandate (reconstruct from flattened fields)
        ComplianceMandate complianceMandate = new ComplianceMandate();
        complianceMandate.requiresKyc = entity.getComplianceRequiresKyc();
        complianceMandate.requiresTaxId = entity.getComplianceRequiresTaxId();
        if (entity.getComplianceAllowedJurisdictions() != null) {
            complianceMandate.allowedJurisdictions = 
                java.util.Arrays.asList(entity.getComplianceAllowedJurisdictions().split(","));
        }
        domain.complianceMandate = complianceMandate;
        
        // Operational Limits (reconstruct from flattened fields)
        OperationalLimits operationalLimits = new OperationalLimits();
        operationalLimits.maxTransactionAmount = entity.getOperationalMaxTransactionAmount();
        operationalLimits.maxSellersPerDay = entity.getOperationalMaxSellersPerDay();
        operationalLimits.globalRateLimit = entity.getOperationalGlobalRateLimit();
        domain.operationalLimits = operationalLimits;
        
        // Localization Policy (reconstruct from flattened fields)
        LocalizationPolicy localizationPolicy = new LocalizationPolicy();
        localizationPolicy.primaryCurrency = entity.getLocalizationPrimaryCurrency();
        localizationPolicy.defaultLanguage = entity.getLocalizationDefaultLanguage();
        localizationPolicy.timezone = entity.getLocalizationTimezone();
        if (entity.getLocalizationSupportedCurrencies() != null) {
            localizationPolicy.supportedCurrencies = 
                java.util.Arrays.asList(entity.getLocalizationSupportedCurrencies().split(","));
        }
        if (entity.getLocalizationSupportedLanguages() != null) {
            localizationPolicy.supportedLanguages = 
                java.util.Arrays.asList(entity.getLocalizationSupportedLanguages().split(","));
        }
        domain.localizationPolicy = localizationPolicy;
        
        return domain;
    }
}
