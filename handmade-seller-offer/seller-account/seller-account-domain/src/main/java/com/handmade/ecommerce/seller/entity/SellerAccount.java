package com.handmade.ecommerce.seller.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_seller_account
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerAccount extends AbstractJpaStateEntity {
    
    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;
    @Column(name = "business_name", length = 255)
    private String businessName;
    @Column(name = "verified")
    private Boolean verified;
    @Column(name = "kyc_completed")
    private Boolean kycCompleted;
    @Column(name = "suspended")
    private Boolean suspended;
    @Column(name = "suspension_reason", length = 500)
    private String suspensionReason;
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "can_create_listings")
    private Boolean canCreateListings;
    @Column(name = "onboarding_policy_id", length = 255)
    private String onboardingPolicyId;
    @Column(name = "onboarding_policy_version", length = 50)
    private String onboardingPolicyVersion;
    @Column(name = "policy_locked_at")
    private Date policyLockedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
