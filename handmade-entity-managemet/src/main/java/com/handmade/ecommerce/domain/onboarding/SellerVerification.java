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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_verification")
public class SellerVerification extends AbstractJpaStateEntity {

    @Column(name = "case_id", length = 36, nullable = false)
    private String caseId;

    @Column(name = "verification_type", length = 50, nullable = false)
    private String verificationType; // IDENTITY, ADDRESS, BUSINESS_LICENSE

    @Column(name = "document_url", length = 255)
    private String documentUrl;

    @Column(name = "verification_provider", length = 100)
    private String verificationProvider; // MANUAL, STRIPE_IDENTITY, THIRD_PARTY

    // verificationStatus managed by STM (PENDING, APPROVED, REJECTED)

    @Column(name = "verified_at")
    private Date verifiedAt;

    @Column(name = "verified_by", length = 100)
    private String verifiedBy;

    @Column(name = "provider_response", columnDefinition = "TEXT")
    private String providerResponse;
}
