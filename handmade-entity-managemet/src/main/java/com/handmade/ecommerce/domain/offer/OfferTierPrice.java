package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * OfferTierPrice - Volume-based pricing tiers
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_offer_tier_price")
public class OfferTierPrice extends BaseJpaEntity {

    @Column(name = "offer_id", length = 36, nullable = false)
    private String offerId;

    @Column(name = "min_quantity", nullable = false)
    private Integer minQuantity;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @Column(name = "tier_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal tierPrice;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
}
