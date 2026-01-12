package com.handmade.ecommerce.seller.onboarding.model;

import java.util.Date;
import lombok.*;
import org.chenile.utils.entity.model.BaseEntity;

/**
 * Pure Domain Model for SellerVerification
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerVerification extends BaseEntity {

    private String sellerId;
    private String verificationType;
    private String status;
    private String provider;
    private String providerVerificationId;
    private String verificationUrl;
    private Date initiatedAt;
    private Date completedAt;
    private Date expiresAt;
    private String verificationResult;
    private String verifiedBy;
    private String rejectionReason;
    private String retryCount;
}
