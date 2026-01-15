package com.handmade.ecommerce.domain.onboarding;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * SellerOnboardingStep - Individual steps within an onboarding case
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_onboarding_step")
public class SellerOnboardingStep extends AbstractJpaStateEntity {

    @Column(name = "case_id", length = 36, nullable = false)
    private String caseId;

    @Column(name = "step_name", length = 100, nullable = false)
    private String stepName; // BUSINESS_INFO, ID_PROOF, TAX_DETAILS

    // status managed by STM (PENDING, SUBMITTED, VERIFIED, FAILED, SKIPPED)

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "data_payload", columnDefinition = "TEXT")
    private String dataPayload; // JSON data for the step

    @Column(name = "step_order")
    private Integer stepOrder;
}
