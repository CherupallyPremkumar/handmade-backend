package com.handmade.ecommerce.promotion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_discount_application
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_discount_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DiscountApplication extends BaseJpaEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "order_line_id", length = 36)
    private String orderLineId;
    @Column(name = "promotion_id", length = 36)
    private String promotionId;
    @Column(name = "coupon_id", length = 36)
    private String couponId;
    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType;
    @Column(name = "discount_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal discountAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "applied_at", nullable = false)
    private Date appliedAt;
    @Column(name = "applied_by", length = 255)
    private String appliedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
