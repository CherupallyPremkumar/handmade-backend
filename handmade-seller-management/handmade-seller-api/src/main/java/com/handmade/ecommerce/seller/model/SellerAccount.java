package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * SellerAccount - Main seller account entity managed by Chenile STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_account",
       indexes = {
           @Index(name = "idx_seller_state", columnList = "stateId"),
           @Index(name = "idx_seller_flow", columnList = "flowId"),
           @Index(name = "idx_seller_platform_state", columnList = "platform_id, stateId")
       })
public class SellerAccount extends AbstractJpaStateEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "kyc_completed")
    private Boolean kycCompleted = false;

    @Column(name = "suspended")
    private Boolean suspended = false;

    @Column(name = "suspension_reason", length = 500)
    private String suspensionReason;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "can_create_listings")
    private Boolean canCreateListings = false;

    @Column(name = "onboarding_policy_id", length = 255)
    private String onboardingPolicyId;

    @Column(name = "onboarding_policy_version", length = 50)
    private String onboardingPolicyVersion;

    @Column(name = "policy_locked_at")
    private Date policyLockedAt;
}
