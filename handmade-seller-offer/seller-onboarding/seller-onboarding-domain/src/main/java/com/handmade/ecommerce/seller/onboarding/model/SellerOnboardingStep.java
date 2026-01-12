package com.handmade.ecommerce.seller.onboarding.model;

import lombok.*;
import org.chenile.utils.entity.model.BaseEntity;

import java.util.Date;

/**
 * Pure Domain Model for SellerOnboardingStep
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerOnboardingStep extends BaseEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SellerOnboardingCase onboardingCase;

    private String stepName;
    private String status;
    private String providerRef;
    private Date lastUpdated;
    private String metadata;
}
