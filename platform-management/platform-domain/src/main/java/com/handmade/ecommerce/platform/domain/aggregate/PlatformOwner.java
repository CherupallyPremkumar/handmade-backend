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

    // Platform Status
    @Embedded
    public PlatformStatus platformStatus;

    // Lifecycle Tracking
    @Embedded
    public PlatformLifecycle platformLifecycle;

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

    /**
     * JPA Required No-Arg Constructor
     */
    public PlatformOwner() {
        this.platformStatus = new PlatformStatus();
        this.platformLifecycle = new PlatformLifecycle();
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
        platform.platformStatus = new PlatformStatus();
        platform.platformLifecycle = new PlatformLifecycle();
        platform.platformLifecycle.createdAt = LocalDateTime.now();
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
        return !deleted && !platformStatus.suspended;
    }

    /**
     * Check if feature is enabled
     */
    public boolean isFeatureEnabled(String featureKey) {
        // TODO: Implement feature flag check
        return true;
    }
}
