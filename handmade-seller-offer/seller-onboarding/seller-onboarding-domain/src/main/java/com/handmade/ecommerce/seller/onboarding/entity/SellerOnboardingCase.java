package com.handmade.ecommerce.seller.onboarding.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_seller_onboarding_case
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added
 * manually
 * after generation. This ensures proper mapping and avoids circular
 * dependencies.
 */
@Entity
@Table(name = "hm_seller_onboarding_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SellerOnboardingCase extends AbstractJpaStateEntity {

    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(name = "business_name", length = 255)
    private String businessName;
    @Column(name = "business_type", length = 100)
    private String businessType;
    @Column(name = "country", length = 100)
    private String country;
    @Column(name = "contact_person", length = 255)
    private String contactPerson;
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;
    @Column(name = "terms_accepted", nullable = false)
    private boolean termsAccepted;
    @Column(name = "current_step", length = 100)
    private String currentStep;
    @Column(name = "completion_percentage", precision = 5, scale = 2)
    private BigDecimal completionPercentage;
    @Column(name = "started_at", nullable = false)
    private Date startedAt;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "approved_at")
    private Date approvedAt;
    @Column(name = "approved_by", length = 255)
    private String approvedBy;
    @Column(name = "rejected_at")
    private Date rejectedAt;
    @Column(name = "rejected_by", length = 255)
    private String rejectedBy;
    @Column(name = "rejection_reason")
    private String rejectionReason;

    @OneToMany(mappedBy = "onboardingCase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SellerOnboardingStep> steps;
}
