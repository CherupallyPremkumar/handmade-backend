package com.handmade.ecommerce.promotion.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Coupon - Coupon codes for promotions
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_coupon")
public class Coupon extends AbstractJpaStateEntity {

    @Column(name = "promotion_id", length = 36, nullable = false)
    private String promotionId;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "per_customer_limit")
    private Integer perCustomerLimit;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
