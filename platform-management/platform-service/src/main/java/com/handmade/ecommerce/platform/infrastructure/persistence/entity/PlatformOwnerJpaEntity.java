package com.handmade.ecommerce.platform.infrastructure.persistence.entity;

import com.handmade.ecommerce.platform.infrastructure.persistence.embeddable.*;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JPA Entity for PlatformOwner.
 * Infrastructure layer - persistence model.
 * Maps to platform_owner table.
 */
@Entity
@Table(name = "platform_owner")
public class PlatformOwnerJpaEntity extends AbstractJpaStateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_state")
    private String lifecycleState;

    @Embedded
    private BrandIdentityEmbeddable brandIdentity;

    @Embedded
    private CorporateIdentityEmbeddable corporateIdentity;

    @Column(name = "current_version")
    private Long currentVersion;

    @Column(name = "current_state_id")
    private String currentStateId;

    @Column(name = "flow_id")
    private String flowId;

    @Column(name = "active_commission_policy_id")
    private String activeCommissionPolicyId;

    @Column(name = "active_feature_config_id")
    private String activeFeatureConfigId;

    // Flattened ComplianceMandate
    @Column(name = "compliance_requires_kyc")
    private Boolean complianceRequiresKyc;

    @Column(name = "compliance_requires_tax_id")
    private Boolean complianceRequiresTaxId;

    @Column(name = "compliance_allowed_jurisdictions", length = 1000)
    private String complianceAllowedJurisdictions; // CSV

    // Flattened OperationalLimits
    @Column(name = "operational_max_transaction_amount", precision = 15, scale = 2)
    private BigDecimal operationalMaxTransactionAmount;

    @Column(name = "operational_max_sellers_per_day")
    private Integer operationalMaxSellersPerDay;

    @Column(name = "operational_global_rate_limit")
    private Integer operationalGlobalRateLimit;

    // Flattened LocalizationPolicy
    @Column(name = "localization_primary_currency", length = 3)
    private String localizationPrimaryCurrency;

    @Column(name = "localization_supported_currencies", length = 500)
    private String localizationSupportedCurrencies;

    @Column(name = "localization_default_language", length = 10)
    private String localizationDefaultLanguage;

    @Column(name = "localization_supported_languages", length = 500)
    private String localizationSupportedLanguages;

    @Column(name = "localization_timezone", length = 50)
    private String localizationTimezone;

    protected PlatformOwnerJpaEntity() {}

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLifecycleState() { return lifecycleState; }
    public void setLifecycleState(String lifecycleState) { this.lifecycleState = lifecycleState; }

    public BrandIdentityEmbeddable getBrandIdentity() { return brandIdentity; }
    public void setBrandIdentity(BrandIdentityEmbeddable brandIdentity) { this.brandIdentity = brandIdentity; }

    public CorporateIdentityEmbeddable getCorporateIdentity() { return corporateIdentity; }
    public void setCorporateIdentity(CorporateIdentityEmbeddable corporateIdentity) { this.corporateIdentity = corporateIdentity; }

    public Long getCurrentVersion() { return currentVersion; }
    public void setCurrentVersion(Long currentVersion) { this.currentVersion = currentVersion; }

    public String getCurrentStateId() { return currentStateId; }
    public void setCurrentStateId(String currentStateId) { this.currentStateId = currentStateId; }

    public String getFlowId() { return flowId; }
    public void setFlowId(String flowId) { this.flowId = flowId; }

    public String getActiveCommissionPolicyId() { return activeCommissionPolicyId; }
    public void setActiveCommissionPolicyId(String activeCommissionPolicyId) { this.activeCommissionPolicyId = activeCommissionPolicyId; }

    public String getActiveFeatureConfigId() { return activeFeatureConfigId; }
    public void setActiveFeatureConfigId(String activeFeatureConfigId) { this.activeFeatureConfigId = activeFeatureConfigId; }

    public Boolean getComplianceRequiresKyc() { return complianceRequiresKyc; }
    public void setComplianceRequiresKyc(Boolean complianceRequiresKyc) { this.complianceRequiresKyc = complianceRequiresKyc; }

    public Boolean getComplianceRequiresTaxId() { return complianceRequiresTaxId; }
    public void setComplianceRequiresTaxId(Boolean complianceRequiresTaxId) { this.complianceRequiresTaxId = complianceRequiresTaxId; }

    public String getComplianceAllowedJurisdictions() { return complianceAllowedJurisdictions; }
    public void setComplianceAllowedJurisdictions(String complianceAllowedJurisdictions) { this.complianceAllowedJurisdictions = complianceAllowedJurisdictions; }

    public BigDecimal getOperationalMaxTransactionAmount() { return operationalMaxTransactionAmount; }
    public void setOperationalMaxTransactionAmount(BigDecimal operationalMaxTransactionAmount) { this.operationalMaxTransactionAmount = operationalMaxTransactionAmount; }

    public Integer getOperationalMaxSellersPerDay() { return operationalMaxSellersPerDay; }
    public void setOperationalMaxSellersPerDay(Integer operationalMaxSellersPerDay) { this.operationalMaxSellersPerDay = operationalMaxSellersPerDay; }

    public Integer getOperationalGlobalRateLimit() { return operationalGlobalRateLimit; }
    public void setOperationalGlobalRateLimit(Integer operationalGlobalRateLimit) { this.operationalGlobalRateLimit = operationalGlobalRateLimit; }

    public String getLocalizationPrimaryCurrency() { return localizationPrimaryCurrency; }
    public void setLocalizationPrimaryCurrency(String localizationPrimaryCurrency) { this.localizationPrimaryCurrency = localizationPrimaryCurrency; }

    public String getLocalizationSupportedCurrencies() { return localizationSupportedCurrencies; }
    public void setLocalizationSupportedCurrencies(String localizationSupportedCurrencies) { this.localizationSupportedCurrencies = localizationSupportedCurrencies; }

    public String getLocalizationDefaultLanguage() { return localizationDefaultLanguage; }
    public void setLocalizationDefaultLanguage(String localizationDefaultLanguage) { this.localizationDefaultLanguage = localizationDefaultLanguage; }

    public String getLocalizationSupportedLanguages() { return localizationSupportedLanguages; }
    public void setLocalizationSupportedLanguages(String localizationSupportedLanguages) { this.localizationSupportedLanguages = localizationSupportedLanguages; }

    public String getLocalizationTimezone() { return localizationTimezone; }
    public void setLocalizationTimezone(String localizationTimezone) { this.localizationTimezone = localizationTimezone; }
}
