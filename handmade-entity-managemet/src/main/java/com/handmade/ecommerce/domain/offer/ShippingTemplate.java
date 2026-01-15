package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * ShippingTemplate - Shipping configuration templates
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipping_template")
public class ShippingTemplate extends BaseJpaEntity {

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "template_name", length = 255, nullable = false)
    private String templateName;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "base_rate", precision = 19, scale = 4)
    private BigDecimal baseRate;

    @Column(name = "per_item_rate", precision = 19, scale = 4)
    private BigDecimal perItemRate;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "free_shipping_threshold", precision = 19, scale = 4)
    private BigDecimal freeShippingThreshold;

    @Column(name = "is_default")
    private Boolean isDefault = false;
}
