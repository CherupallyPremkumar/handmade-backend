package com.handmade.ecommerce.promotion.pricing.engine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pricing_priority
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pricing_priority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PricingPriority extends BaseJpaEntity {
    
    @Column(name = "rule_id", nullable = false, length = 36)
    private String ruleId;
    @Column(name = "priority_score")
    private String priorityScore;
    @Column(name = "stacking_group", length = 50)
    private String stackingGroup;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
