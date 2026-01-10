package com.handmade.ecommerce.seller.onboarding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.util.Date;

@Entity
@Table(name = "hm_seller_onboarding_step")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerOnboardingStep extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SellerOnboardingCase onboardingCase;

    @Column(name = "step_name", nullable = false, length = 50)
    private String stepName;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "provider_ref", length = 255)
    private String providerRef;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
}
