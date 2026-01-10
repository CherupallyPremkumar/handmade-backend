package com.handmade.ecommerce.seller.offer.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_shipping_rule
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_shipping_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ShippingRule extends BaseJpaEntity {
    
    @Column(name = "template_id", nullable = false, length = 36)
    private String templateId;
    @Column(name = "shipping_option", length = 50)
    private String shippingOption;
    @Column(name = "cost_model", length = 50)
    private String costModel;
    @Column(name = "base_cost", precision = 19, scale = 4)
    private BigDecimal baseCost;
    @Column(name = "per_item_cost", precision = 19, scale = 4)
    private BigDecimal perItemCost;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
