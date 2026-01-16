package com.handmade.ecommerce.offer.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * ShippingRule - Shipping rules for specific regions/conditions
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipping_rule")
public class ShippingRule extends BaseJpaEntity {

    @Column(name = "template_id", length = 36, nullable = false)
    private String templateId;

    @Column(name = "shipping_option", length = 50)
    private String shippingOption;

    @Column(name = "cost_model", length = 50)
    private String costModel;

    @Column(name = "base_cost", precision = 19, scale = 4)
    private BigDecimal baseCost;

    @Column(name = "per_item_cost", precision = 19, scale = 4)
    private BigDecimal perItemCost;
}
