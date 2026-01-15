package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * BuyBoxEligibility - Offer eligibility for buy box placement
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_buy_box_eligibility")
public class BuyBoxEligibility extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false, unique = true)
    private String offerId;

    @Column(name = "is_eligible")
    private Boolean isEligible = false;

    @Column(name = "eligibility_score")
    private Integer eligibilityScore;

    @Column(name = "ineligibility_reason", columnDefinition = "TEXT")
    private String ineligibilityReason;

    @Column(name = "is_buy_box_winner")
    private Boolean isBuyBoxWinner = false;
}
