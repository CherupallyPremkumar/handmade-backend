package com.handmade.ecommerce.promotion.pricing.engine.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pricing_rule_action
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pricing_rule_action")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PricingRuleAction extends BaseJpaEntity {
    
    @Column(name = "rule_id", nullable = false, length = 36)
    private String ruleId;
    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;
    @Column(name = "value", nullable = false, precision = 19, scale = 4)
    private BigDecimal value;
    @Column(name = "currency", length = 3)
    private String currency;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
