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
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_seller_onboarding_step")
public class SellerOnboardingStep extends AbstractJpaStateEntity {

    @Column(name = "onboarding_case_id", length = 36, nullable = false)
    private String onboardingCaseId;

    @Column(name = "step_name", length = 100, nullable = false)
    private String stepName; // BUSINESS_INFO, ID_PROOF, TAX_DETAILS

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Column(name = "is_required")
    private Boolean isRequired = true;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "completed_at")
    private Date completedAt;

    @Column(name = "skipped_at")
    private Date skippedAt;

    @Column(name = "skip_reason", length = 500)
    private String skipReason;

    @Lob
    @Column(name = "data_payload")
    private String dataPayload; // JSON data for the step
}
