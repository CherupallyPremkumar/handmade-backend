package com.handmade.ecommerce.domain.offer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * ShippingRule - Shipping rules for specific regions/conditions
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipping_rule")
public class ShippingRule extends BaseJpaEntity {

    @Column(name = "template_id", length = 36, nullable = false)
    private String templateId;

    @Column(name = "region_code", length = 10)
    private String regionCode;

    @Column(name = "postal_code_pattern", length = 100)
    private String postalCodePattern;

    @Column(name = "shipping_rate", precision = 19, scale = 4)
    private BigDecimal shippingRate;

    @Column(name = "additional_item_rate", precision = 19, scale = 4)
    private BigDecimal additionalItemRate;

    @Column(name = "min_delivery_days")
    private Integer minDeliveryDays;

    @Column(name = "max_delivery_days")
    private Integer maxDeliveryDays;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
