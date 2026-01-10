package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_buy_box_eligibility
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_buy_box_eligibility")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BuyBoxEligibility extends BaseJpaEntity {
    
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "score", nullable = false, precision = 5, scale = 4)
    private BigDecimal score;
    @Column(name = "is_eligible", nullable = false)
    private Boolean isEligible;
    @Column(name = "ineligibility_reason", length = 255)
    private String ineligibilityReason;
    @Column(name = "evaluated_at", nullable = false)
    private Date evaluatedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
