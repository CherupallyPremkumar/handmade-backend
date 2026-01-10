package com.handmade.ecommerce.promotion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_coupon_usage
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_coupon_usage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CouponUsage extends BaseJpaEntity {
    
    @Column(name = "coupon_id", nullable = false, length = 36)
    private String couponId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "discount_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal discountAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "used_at", nullable = false)
    private Date usedAt;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "revoked_at")
    private Date revokedAt;
    @Column(name = "revoke_reason", length = 500)
    private String revokeReason;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
