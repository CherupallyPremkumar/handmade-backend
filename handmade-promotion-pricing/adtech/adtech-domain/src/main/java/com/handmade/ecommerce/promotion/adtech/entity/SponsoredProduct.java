package com.handmade.ecommerce.promotion.adtech.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_sponsored_product
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_sponsored_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SponsoredProduct extends BaseJpaEntity {
    
    @Column(name = "campaign_id", nullable = false, length = 36)
    private String campaignId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "bid_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal bidAmount;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
