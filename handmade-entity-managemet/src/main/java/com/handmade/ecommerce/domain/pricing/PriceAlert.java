package com.handmade.ecommerce.domain.pricing;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PriceAlert - Customer alerts for price drops
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_price_alert")
public class PriceAlert extends AbstractJpaStateEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "target_price", precision = 19, scale = 4)
    private BigDecimal targetPrice;

    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "email", length = 255)
    private String email; // Optional if customer_id acts as primary contact

    @Column(name = "notification_sent")
    private Boolean notificationSent = false;
}
