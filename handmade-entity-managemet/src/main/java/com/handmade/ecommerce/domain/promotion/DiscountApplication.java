package com.handmade.ecommerce.domain.promotion;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * DiscountApplication - Records discounts applied to orders
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_discount_application")
public class DiscountApplication extends BaseJpaEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "order_line_id", length = 36)
    private String orderLineId;

    @Column(name = "promotion_id", length = 36, nullable = false)
    private String promotionId;

    @Column(name = "coupon_code", length = 50)
    private String couponCode;

    @Column(name = "discount_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "description", length = 255)
    private String description;
}
