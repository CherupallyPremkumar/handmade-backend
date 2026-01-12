package com.handmade.ecommerce.seller.onboarding.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.Date;

/**
 * JPA Entity for hm_seller_verification
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_seller_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerVerificationEntity extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "verification_type", nullable = false, length = 100)
    private String verificationType;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "provider", length = 100)
    private String provider;
    @Column(name = "provider_verification_id", length = 255)
    private String providerVerificationId;
    @Column(name = "verification_url", length = 2048)
    private String verificationUrl;
    @Column(name = "initiated_at", nullable = false)
    private Date initiatedAt;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "verification_result")
    private String verificationResult;
    @Column(name = "verified_by", length = 255)
    private String verifiedBy;
    @Column(name = "rejection_reason")
    private String rejectionReason;
    @Column(name = "retry_count")
    private String retryCount;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
