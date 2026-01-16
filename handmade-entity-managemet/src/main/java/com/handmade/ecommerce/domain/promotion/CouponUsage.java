package com.handmade.ecommerce.domain.promotion;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * CouponUsage - Tracks coupon usage by customers
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_coupon_usage")
public class CouponUsage extends BaseJpaEntity {

    @Column(name = "coupon_id", length = 36, nullable = false)
    private String couponId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "usage_date", nullable = false)
    private Date usageDate;
}
