package com.handmade.ecommerce.seller.domain.aggregate;

import com.handmade.ecommerce.seller.domain.valueobject.BankingInfo;
import com.handmade.ecommerce.seller.domain.valueobject.BusinessDetails;
import com.handmade.ecommerce.seller.domain.valueobject.SellerMetrics;
import com.handmade.ecommerce.seller.domain.valueobject.SellerProfile;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Seller Account Aggregate Root
 * Manages complete seller lifecycle and state transitions
 * 
 * Mirrors platform-management pattern
 */
@Entity
@Table(name = "seller_account")
public class SellerAccount extends AbstractJpaStateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", length = 36)
    public String id;
    
    // Platform association
    @Column(name = "platform_id", nullable = false, length = 36)
    public String platformId;
    
    // Identity
    @Column(unique = true, nullable = false)
    public String email;
    
    @Column(name = "business_name")
    public String businessName;
    
    // Status flags
    @Column(name = "verified")
    public boolean verified;
    
    @Column(name = "kyc_completed")
    public boolean kycCompleted;
    
    @Column(name = "suspended")
    public boolean suspended;
    
    @Column(name = "suspension_reason")
    public String suspensionReason;
    
    @Column(name = "deleted")
    public boolean deleted;
    
    @Column(name = "can_create_listings")
    public boolean canCreateListings;
    
    // Value Objects (Embedded)
    @Embedded
    public SellerProfile profile;
    
    @Embedded
    public BusinessDetails businessDetails;
    
    @Embedded
    public BankingInfo bankingInfo;
    
    @Embedded
    public SellerMetrics metrics;
    
    // Relationships
    @Column(name = "active_subscription_plan_id")
    public String activeSubscriptionPlanId;
    
    // Timestamps
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    // ========== ONBOARDING POLICY LOCK (Week 1: Policy Versioning) ==========
    
    /**
     * Onboarding policy this seller is locked to
     * IMMUTABLE: Set at onboarding start, never changes
     * Purpose: Audit trail - proves which rules applied during onboarding
     */
    @Column(name = "onboarding_policy_id", updatable = false)
    public String onboardingPolicyId;
    
    /**
     * Denormalized policy version for query performance
     * IMMUTABLE: Set at onboarding start, never changes
     * Example: "2024.1-US-INDIVIDUAL"
     */
    @Column(name = "onboarding_policy_version", length = 50, updatable = false)
    public String onboardingPolicyVersion;
    
    /**
     * When seller was locked to this policy
     * IMMUTABLE: Legal timestamp for compliance
     */
    @Column(name = "policy_locked_at", updatable = false)
    public LocalDateTime policyLockedAt;

    public SellerAccount() {
        this.verified = false;
        this.kycCompleted = false;
        this.suspended = false;
        this.deleted = false;
        this.canCreateListings = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        // Initialize value objects
        this.profile = new SellerProfile();
        this.businessDetails = new BusinessDetails();
        this.bankingInfo = new BankingInfo();
        this.metrics = new SellerMetrics();
    }

    // Domain methods
    
    /**
     * Check if seller can accept orders
     */
    public boolean canAcceptOrders() {
        return verified 
            && !suspended 
            && !deleted;
            //&& "ACTIVE".equals(getState());  // TODO: Add state check
    }
    
    /**
     * Check if seller needs auto-suspension
     */
    public boolean requiresAutoSuspension() {
        return metrics != null && metrics.requiresAutoSuspension();
    }
    
    /**
     * Update seller tier based on performance
     */
    public void updateTier() {
        if (metrics != null) {
            metrics.setTier(metrics.calculateTier());
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isKycCompleted() {
        return kycCompleted;
    }

    public void setKycCompleted(boolean kycCompleted) {
        this.kycCompleted = kycCompleted;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getSuspensionReason() {
        return suspensionReason;
    }

    public void setSuspensionReason(String suspensionReason) {
        this.suspensionReason = suspensionReason;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isCanCreateListings() {
        return canCreateListings;
    }

    public void setCanCreateListings(boolean canCreateListings) {
        this.canCreateListings = canCreateListings;
    }

    public SellerProfile getProfile() {
        return profile;
    }

    public void setProfile(SellerProfile profile) {
        this.profile = profile;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public BankingInfo getBankingInfo() {
        return bankingInfo;
    }

    public void setBankingInfo(BankingInfo bankingInfo) {
        this.bankingInfo = bankingInfo;
    }

    public SellerMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(SellerMetrics metrics) {
        this.metrics = metrics;
    }

    public String getActiveSubscriptionPlanId() {
        return activeSubscriptionPlanId;
    }

    public void setActiveSubscriptionPlanId(String activeSubscriptionPlanId) {
        this.activeSubscriptionPlanId = activeSubscriptionPlanId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Policy lock getters and setters
    
    public String getOnboardingPolicyId() {
        return onboardingPolicyId;
    }
    
    public void setOnboardingPolicyId(String onboardingPolicyId) {
        this.onboardingPolicyId = onboardingPolicyId;
    }
    
    public String getOnboardingPolicyVersion() {
        return onboardingPolicyVersion;
    }
    
    public void setOnboardingPolicyVersion(String onboardingPolicyVersion) {
        this.onboardingPolicyVersion = onboardingPolicyVersion;
    }
    
    public LocalDateTime getPolicyLockedAt() {
        return policyLockedAt;
    }
    
    public void setPolicyLockedAt(LocalDateTime policyLockedAt) {
        this.policyLockedAt = policyLockedAt;
    }
}
