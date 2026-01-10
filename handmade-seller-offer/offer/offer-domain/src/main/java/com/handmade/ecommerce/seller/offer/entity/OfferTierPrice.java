package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_offer_tier_price
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_offer_tier_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OfferTierPrice extends BaseJpaEntity {
    
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "min_quantity", nullable = false)
    private String minQuantity;
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    @Column(name = "fixed_price", precision = 19, scale = 4)
    private BigDecimal fixedPrice;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
