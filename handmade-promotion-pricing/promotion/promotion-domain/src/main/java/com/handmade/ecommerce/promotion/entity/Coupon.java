package com.handmade.ecommerce.promotion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_coupon
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_coupon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Coupon extends BaseJpaEntity {
    
    @Column(name = "coupon_code", nullable = false, length = 100, unique = true)
    private String couponCode;
    @Column(name = "promotion_id", nullable = false, length = 36)
    private String promotionId;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType;
    @Column(name = "discount_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal discountValue;
    @Column(name = "max_discount_amount", precision = 19, scale = 4)
    private BigDecimal maxDiscountAmount;
    @Column(name = "min_purchase_amount", precision = 19, scale = 4)
    private BigDecimal minPurchaseAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "valid_from", nullable = false)
    private Date validFrom;
    @Column(name = "valid_until", nullable = false)
    private Date validUntil;
    @Column(name = "max_uses_per_customer")
    private String maxUsesPerCustomer;
    @Column(name = "max_total_uses")
    private String maxTotalUses;
    @Column(name = "current_uses")
    private String currentUses;
    @Column(name = "is_single_use")
    private Boolean isSingleUse;
    @Column(name = "target_customer_id", length = 36)
    private String targetCustomerId;
    @Column(name = "target_customer_segment", length = 100)
    private String targetCustomerSegment;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
