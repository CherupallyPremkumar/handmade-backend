package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * BuyBoxEligibility - Offer eligibility for buy box placement
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_buy_box_eligibility")
public class BuyBoxEligibility extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "is_eligible", nullable = false)
    private Boolean isEligible = true;

    @Column(name = "score", precision = 5, scale = 4, nullable = false)
    private java.math.BigDecimal score;

    @Column(name = "ineligibility_reason", length = 255)
    private String ineligibilityReason;

    @Column(name = "evaluated_at", nullable = false)
    private java.util.Date evaluatedAt;
}
