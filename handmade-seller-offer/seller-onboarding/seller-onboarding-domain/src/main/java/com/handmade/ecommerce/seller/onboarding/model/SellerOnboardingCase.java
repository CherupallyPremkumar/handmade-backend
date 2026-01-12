package com.handmade.ecommerce.seller.onboarding.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.*;
import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

/**
 * Pure Domain Model for SellerOnboardingCase
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerOnboardingCase extends AbstractExtendedStateEntity {

    private String sellerId;
    private String email;
    private String businessName;
    private String businessType;
    private String country;
    private String contactPerson;
    private String phoneNumber;
    private boolean termsAccepted;
    private String currentStep;
    private BigDecimal completionPercentage;
    private Date startedAt;
    private Date completedAt;
    private Date approvedAt;
    private String approvedBy;
    private Date rejectedAt;
    private String rejectedBy;
    private String rejectionReason;

    private List<SellerOnboardingStep> steps;
}
