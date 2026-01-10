package com.handmade.ecommerce.promotion.pricing.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_price_alert
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_price_alert")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PriceAlert extends BaseJpaEntity {
    
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "target_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal targetPrice;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "alert_type", length = 50)
    private String alertType;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Column(name = "triggered_at")
    private Date triggeredAt;
    @Column(name = "notified_at")
    private Date notifiedAt;
    @Column(name = "expires_at")
    private Date expiresAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
