package com.handmade.ecommerce.platform.domain.aggregate;

import org.chenile.stm.StateEntity;
import com.handmade.ecommerce.platform.domain.event.*;
import com.handmade.ecommerce.platform.domain.valueobject.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * The PlatformOwner represents the single authoritative entity that owns and governs the marketplace.
 * Aggregate Root - PURE DOMAIN MODEL (no persistence annotations).
 */
public class PlatformOwner implements StateEntity, Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SINGLETON_ID = "PLATFORM_ROOT";

    // Domain Identity
    protected String id;
    
    private String name;
    private PlatformLifecycle lifecycleState;
    private BrandIdentity brandIdentity;
    private CorporateIdentity corporateIdentity;
    private Long currentVersion;
    private String currentStateId;
    private String flowId;

    // Chenile workflow support
    public org.chenile.workflow.model.TransientMap transientMap = new org.chenile.workflow.model.TransientMap();

    // References to the currently active policies
    private String activeCommissionPolicyId;
    private String activeFeatureConfigId;

    private ComplianceMandate complianceMandate;
    private OperationalLimits operationalLimits;
    private LocalizationPolicy localizationPolicy;

    // Domain events (not persisted)
    private java.util.List<Object> domainEvents = new java.util.ArrayList<>();

    // --- Constructors ---

    /**
     * JPA Required No-Arg Constructor. Protected to prevent direct instantiation.
     */
    protected PlatformOwner() {
    }

    /**
     * Private constructor for Bootstrap Factory.
     */
    private PlatformOwner(String name, BrandIdentity brandIdentity, CorporateIdentity corporateIdentity, ComplianceMandate complianceMandate, OperationalLimits operationalLimits, LocalizationPolicy localizationPolicy) {
        this.id = SINGLETON_ID;
        this.name = name;
        this.brandIdentity = brandIdentity;
        this.corporateIdentity = corporateIdentity;
        this.complianceMandate = complianceMandate;
        this.operationalLimits = operationalLimits;
        this.localizationPolicy = localizationPolicy;
        this.lifecycleState = PlatformLifecycle.PROVISIONING;
        this.currentVersion = 1L;
        this.domainEvents.add(new PlatformStatusChanged(
            java.util.UUID.nameUUIDFromBytes(SINGLETON_ID.getBytes()), 
            null, 
            PlatformLifecycle.PROVISIONING, 
            "Platform Bootstrapped", 
            "SYSTEM", 
            Instant.now()
        ));
    }

    /**
     * Factory method to bootstrap the singleton platform.
     * This is the ONLY legitimate way to create a new PlatformOwner.
     */
    public static PlatformOwner bootstrap(String name, BrandIdentity brandIdentity, CorporateIdentity corporateIdentity, ComplianceMandate complianceMandate, OperationalLimits operationalLimits, LocalizationPolicy localizationPolicy) {
        return new PlatformOwner(name, brandIdentity, corporateIdentity, complianceMandate, operationalLimits, localizationPolicy);
    }

    // --- Business Behaviors ---

    public void activate(String operatorId) {
        if (this.lifecycleState != PlatformLifecycle.PROVISIONING) {
             // Idempotent check or error? "Platform already activated" implies error.
             if (this.lifecycleState == PlatformLifecycle.LIVE) return; 
             throw new IllegalStateException("Platform can only be activated from PROVISIONING state.");
        }
        
        PlatformLifecycle oldState = this.lifecycleState;
        this.lifecycleState = PlatformLifecycle.LIVE;
        
        this.domainEvents.add(new PlatformStatusChanged(
            java.util.UUID.fromString(getDefaultUuid()), oldState, PlatformLifecycle.LIVE, "Platform Activated", operatorId, Instant.now()
        ));
    }

    public void imposeSanctions(String reason, String operatorId) {
        if (this.lifecycleState == PlatformLifecycle.SUNSET) return;
        PlatformLifecycle oldState = this.lifecycleState;
        this.lifecycleState = PlatformLifecycle.RESTRICTED;
        
        this.domainEvents.add(new PlatformStatusChanged(
            java.util.UUID.fromString(getDefaultUuid()), oldState, PlatformLifecycle.RESTRICTED, reason, operatorId, Instant.now()
        ));
    }

    public void rebrand(BrandIdentity newBrand) {
        validateWriteAccess();
        this.brandIdentity = newBrand;
    }

    public void reviseCommissionStructure(String newPolicyId, Date effectiveDate, String reason) {
        validateWriteAccess();
        if (newPolicyId == null) throw new IllegalArgumentException("New Policy ID cannot be null");
        
        this.activeCommissionPolicyId = newPolicyId;
        this.domainEvents.add(new CommissionStructureRevised(
            java.util.UUID.fromString(getDefaultUuid()), java.util.UUID.fromString(newPolicyId), effectiveDate.toInstant(), reason, Instant.now()
        ));
    }

    public void reconfigureCapabilities(String newConfigId, String reason) {
        validateWriteAccess();
        if (newConfigId == null) throw new IllegalArgumentException("Config ID cannot be null");
        this.activeFeatureConfigId = newConfigId;
    }

    public void enforceComplianceMandate(ComplianceMandate mandate) {
        validateWriteAccess();
        if (mandate == null) throw new IllegalArgumentException("Mandate cannot be null");
        this.complianceMandate = mandate;
    }

    public void updateOperationalLimits(OperationalLimits limits) {
        validateWriteAccess();
        if (limits == null) throw new IllegalArgumentException("Limits cannot be null");
        this.operationalLimits = limits;
    }

    private void validateWriteAccess() {
        if (this.lifecycleState != null && !this.lifecycleState.allowsWrites()) {
            throw new IllegalStateException("Platform is in read-only mode.");
        }
    }
    
    // Helper to get UUID from ID String (for events)
    private String getDefaultUuid() {
        return java.util.UUID.nameUUIDFromBytes(SINGLETON_ID.getBytes()).toString();
    }


    // --- Accessors (Getters Only where possible) ---

    public String getName() { return name; }
    // setName removed - use rebrand or distinct method if renaming is allowed (usually not for Legal Name)

    public PlatformLifecycle getLifecycleState() { return lifecycleState; }
    
    public BrandIdentity getBrandIdentity() { return brandIdentity; }
    public CorporateIdentity getCorporateIdentity() { return corporateIdentity; }

    public Long getCurrentVersion() { return currentVersion; }
    public void setCurrentVersion(Long currentVersion) { this.currentVersion = currentVersion; } // JPA needs setter or field access

    public String getCurrentStateId() { return currentStateId; }
    public void setCurrentStateId(String currentStateId) { this.currentStateId = currentStateId; }
    
    @Override
    public org.chenile.stm.State getCurrentState() { return null; }
    @Override
    public void setCurrentState(org.chenile.stm.State state) { 
        if (state != null) this.currentStateId = state.getStateId(); 
    }

    public String getFlowId() { return flowId; }
    public void setFlowId(String flowId) { this.flowId = flowId; }

    public String getActiveCommissionPolicyId() { return activeCommissionPolicyId; }
    public String getActiveFeatureConfigId() { return activeFeatureConfigId; }
    public ComplianceMandate getComplianceMandate() { return complianceMandate; }
    public OperationalLimits getOperationalLimits() { return operationalLimits; }
    public LocalizationPolicy getLocalizationPolicy() { return localizationPolicy; }

    public void clearEvents() { this.domainEvents.clear(); }
    public java.util.List<Object> getDomainEvents() { return java.util.Collections.unmodifiableList(domainEvents); }
    
    // Identity methods (required since we removed AbstractJpaStateEntity)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
