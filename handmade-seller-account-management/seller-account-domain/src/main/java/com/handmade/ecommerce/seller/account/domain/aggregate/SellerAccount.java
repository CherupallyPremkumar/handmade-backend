package com.handmade.ecommerce.seller.account.domain.aggregate;

import com.handmade.ecommerce.seller.account.domain.enums.VerificationStatus;
import com.handmade.ecommerce.seller.account.domain.valueobject.BusinessDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.workflow.activities.model.ActivityEnabledStateEntity;
import org.chenile.workflow.activities.model.ActivityLog;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "hm_seller_account", uniqueConstraints = {
                @UniqueConstraint(name = "uk_acc_email_country_active", columnNames = { "email", "country",
                                "is_active" })
}, indexes = {
                @Index(name = "idx_acc_email_country", columnList = "email,country"),
                @Index(name = "idx_acc_state", columnList = "state")
})
@Getter
@Setter
public class SellerAccount extends AbstractJpaStateEntity
                implements Serializable, ActivityEnabledStateEntity {

        private static final long serialVersionUID = 1L;

        @Column(name = "email", nullable = false, length = 320)
        private String email;

        @Column(name = "business_name", length = 255)
        private String businessName;

        @Embedded
        private BusinessDetails businessDetails = new BusinessDetails();

        @Column(name = "country", nullable = false, length = 2)
        private String country;

        @Column(name = "seller_type", nullable = false, length = 20)
        private String sellerType;

        @Column(name = "policy_id", nullable = false)
        private String policyId;

        @Column(name = "policy_version", nullable = false)
        private String policyVersion;

        @Column(name = "onboarding_policy_id")
        private String onboardingPolicyId;

        @Column(name = "onboarding_policy_version")
        private String onboardingPolicyVersion;

        @Column(name = "policy_locked_at")
        private LocalDateTime policyLockedAt;

        @Column(name = "identity_required", nullable = false)
        private boolean identityVerificationRequired;

        @Column(name = "tax_required", nullable = false)
        private boolean taxVerificationRequired;

        @Column(name = "bank_required", nullable = false)
        private boolean bankVerificationRequired;

        @Column(name = "identity_verified")
        private boolean identityVerified;

        @Column(name = "identity_verified_at")
        private Instant identityVerifiedAt;

        @Column(name = "manual_approval_required", nullable = false)
        private boolean manualApprovalRequired;

        @Column(name = "is_active", nullable = false)
        private boolean active;

        @Enumerated(EnumType.STRING)
        @Column(name = "identity_status")
        private VerificationStatus identityStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "tax_status")
        private VerificationStatus taxStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "bank_status")
        private VerificationStatus bankStatus;

        @Column(name = "external_payout_token")
        private String externalPayoutToken;

        @Column(name = "external_payout_provider")
        private String externalPayoutProvider;

        @Column(name = "bank_details_provided")
        public boolean bankDetailsProvided;

        @Column(name = "completed_at")
        private Instant completedAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "kyc_provider")
        private String kycProvider;

        @Column(name = "kyc_verification_id")
        private String kycVerificationId;

        @Column(name = "kyc_confidence_score")
        private Double kycConfidenceScore;

        @Column(name = "document_upload_id")
        private String documentUploadId;

        @Column(name = "identity_verification_attempts")
        private int identityVerificationAttempts = 0;

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
        @JoinColumn(name = "account_id")
        private List<OnboardingActivityLog> activities = new ArrayList<>();

        @Override
        public Collection<ActivityLog> obtainActivities() {
                return new ArrayList<>(activities);
        }

        @Override
        public ActivityLog addActivity(String eventId, String comment) {
                OnboardingActivityLog log = new OnboardingActivityLog();
                log.activityName = eventId;
                log.activityComment = comment;
                log.activitySuccess = true;
                this.activities.add(log);
                return log;
        }

        /**
         * Increment identity verification attempts
         */
        public void incrementIdentityVerificationAttempts() {
                this.identityVerificationAttempts++;
        }

        // Business Methods for Identity Verification

        /**
         * Mark identity as verified
         * Called by STM action when Stripe verification succeeds
         */
        public void markIdentityVerified() {
                this.identityVerified = true;
                this.identityVerifiedAt = Instant.now();
                this.updatedAt = LocalDateTime.now();
                this.identityStatus = VerificationStatus.VERIFIED;
        }

        /**
         * Mark identity as rejected
         * Called by STM action when Stripe verification fails
         */
        public void markIdentityRejected() {
                this.identityVerified = false;
                this.identityVerifiedAt = null;
                this.updatedAt = LocalDateTime.now();
                this.identityStatus = VerificationStatus.REJECTED;
        }

        /**
         * Check if ready for tax verification
         */
        public boolean isReadyForTaxVerification() {
                return this.identityVerified;
        }

        // --- Manual Getters/Setters to ensure availability without Lombok ---

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

        public BusinessDetails getBusinessDetails() {
                return businessDetails;
        }

        public void setBusinessDetails(BusinessDetails businessDetails) {
                this.businessDetails = businessDetails;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public boolean isIdentityVerified() {
                return identityVerified;
        }

        public void setIdentityVerified(boolean identityVerified) {
                this.identityVerified = identityVerified;
        }

        public Instant getIdentityVerifiedAt() {
                return identityVerifiedAt;
        }

        public void setIdentityVerifiedAt(Instant identityVerifiedAt) {
                this.identityVerifiedAt = identityVerifiedAt;
        }

        public void setIdentityVerifiedAt(LocalDateTime ldt) {
                if (ldt == null) {
                        this.identityVerifiedAt = null;
                } else {
                        this.identityVerifiedAt = ldt.atZone(ZoneId.systemDefault()).toInstant();
                }
        }

        public String getExternalPayoutToken() {
                return externalPayoutToken;
        }

        public void setExternalPayoutToken(String externalPayoutToken) {
                this.externalPayoutToken = externalPayoutToken;
        }

        public String getExternalPayoutProvider() {
                return externalPayoutProvider;
        }

        public void setExternalPayoutProvider(String externalPayoutProvider) {
                this.externalPayoutProvider = externalPayoutProvider;
        }

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
