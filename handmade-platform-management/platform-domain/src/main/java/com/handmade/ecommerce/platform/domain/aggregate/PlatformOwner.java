package com.handmade.ecommerce.platform.domain.aggregate;

import com.handmade.ecommerce.platform.domain.valueobject.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Platform Owner Aggregate Root
 * Represents the single authoritative entity that owns and governs the marketplace
 * Extends AbstractJpaStateEntity for state machine support (like chenile Process)
 * 
 * State management is inherited from AbstractJpaStateEntity:
 * - state (flowId + stateId) - Current state in state machine
 * - stateEntryTime - When entered current state
 * - SLA tracking fields
 */
@Entity
@Table(name = "platform_owner")
public class PlatformOwner extends AbstractJpaStateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SINGLETON_ID = "PLATFORM_ROOT";

    // Core Identity
    @Column(name = "name", nullable = false)
    public String name;

    // Brand & Corporate Identity (Embedded)
    @Embedded
    public BrandIdentity brandIdentity;

    @Embedded
    public CorporateIdentity corporateIdentity;

    // Localization & Compliance
    @Embedded
    public LocalizationPolicy localizationPolicy;

    @Embedded
    public ComplianceMandate complianceMandate;

    // Operational Configuration
    @Embedded
    public OperationalLimits operationalLimits;

    // Active Policy References
    @Column(name = "active_commission_policy_id")
    public String activeCommissionPolicyId;

    @Column(name = "active_feature_config_id")
    public String activeFeatureConfigId;

    // Soft Delete Support
    @Column(name = "deleted")
    public boolean deleted = false;

    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    public String deletedBy;

    // Suspension tracking (business logic, not state machine)
    @Column(name = "suspended")
    public boolean suspended = false;

    @Column(name = "suspension_reason")
    public String suspensionReason;

    /**
     * JPA Required No-Arg Constructor
     */
    public PlatformOwner() {
    }

    /**
     * Factory method to bootstrap the platform
     */
    public static PlatformOwner bootstrap(String name, 
                                         BrandIdentity brandIdentity, 
                                         CorporateIdentity corporateIdentity,
                                         LocalizationPolicy localizationPolicy,
                                         ComplianceMandate complianceMandate,
                                         OperationalLimits operationalLimits) {
        PlatformOwner platform = new PlatformOwner();
        platform.id = SINGLETON_ID;
        platform.name = name;
        platform.brandIdentity = brandIdentity;
        platform.corporateIdentity = corporateIdentity;
        platform.localizationPolicy = localizationPolicy;
        platform.complianceMandate = complianceMandate;
        platform.operationalLimits = operationalLimits;
        return platform;
    }

    /**
     * Override getPrefix for ID generation
     */
    @Override
    protected String getPrefix() {
        return "PLATFORM";
    }

    /**
     * Check if platform allows write operations
     */
    public boolean allowsWrites() {
        return !deleted && !suspended;
    }

    /**
     * Check if feature is enabled
     */
    public boolean isFeatureEnabled(String featureKey) {
        // TODO: Implement feature flag check
        return true;
    }
}
