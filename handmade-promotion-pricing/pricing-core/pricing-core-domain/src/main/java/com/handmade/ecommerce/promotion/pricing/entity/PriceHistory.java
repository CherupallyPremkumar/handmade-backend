package com.handmade.ecommerce.promotion.pricing.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_price_history
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_price_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PriceHistory extends BaseJpaEntity {
    
    @Column(name = "offer_id", nullable = false, length = 36)
    private String offerId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "effective_from", nullable = false)
    private Date effectiveFrom;
    @Column(name = "effective_to")
    private Date effectiveTo;
    @Column(name = "change_reason", length = 500)
    private String changeReason;
    @Column(name = "changed_by", length = 255)
    private String changedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
