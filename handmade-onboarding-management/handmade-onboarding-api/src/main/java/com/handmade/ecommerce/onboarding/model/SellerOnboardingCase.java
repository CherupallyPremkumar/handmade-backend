package com.handmade.ecommerce.onboarding.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * SellerOnboardingCase - Represents a seller's application process
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_onboarding_case")
public class SellerOnboardingCase extends AbstractJpaStateEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @Column(name = "business_type", length = 100)
    private String businessType;

    @Column(name = "contact_person", length = 255)
    private String contactPerson;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    // onboardingStatus managed by STM (INITIATED, IN_PROGRESS, COMPLETED, REJECTED)

    @Column(name = "current_step", length = 100)
    private String currentStep; // Using STM state or this field for granular step tracking

    @Column(name = "completion_percentage", precision = 5, scale = 2)
    private java.math.BigDecimal completionPercentage;

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

    @Lob
    @Column(name = "rejection_reason")
    private String rejectionReason;
}
