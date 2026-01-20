package com.handmade.ecommerce.domain.onboarding;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * SellerVerification - Verification of specific documents or details
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_verification")
public class SellerVerification extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "verification_type", length = 100, nullable = false)
    private String verificationType; // IDENTITY, ADDRESS, BUSINESS_LICENSE

    @Column(name = "provider", length = 100)
    private String provider; // MANUAL, STRIPE_IDENTITY, THIRD_PARTY

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

    @Lob
    @Column(name = "verification_result")
    private String verificationResult;

    @Column(name = "verified_by", length = 255)
    private String verifiedBy;

    @Lob
    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "retry_count")
    private Integer retryCount = 0;
}
